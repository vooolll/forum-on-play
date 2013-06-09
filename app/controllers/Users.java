package controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import models.User;
import play.data.Form;
import play.data.validation.Constraints;
import play.db.ebean.Model.Finder;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.errors.page404;
import views.html.main.success;
import views.html.users.login;
import views.html.users.registration;
import views.html.users.registrationLetter;
import views.html.users.show;
import views.html.users.cabinet.index;
import views.html.users.changePass;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;


public class Users extends Controller {
    /*
     * Форма для регистрации пользователя
     */
    public static class Registration {

        @Constraints.MinLength(3)
        @Constraints.MaxLength(64)
        @Constraints.Required
        public String fullName;

        @Constraints.Email
        @Constraints.Required
        public String email;

        public String validate() {
            // другой способ см. http://stackoverflow.com/questions/12404088/how-do-i-set-a-custom-validation-error-on-a-specific-form-field-in-play-2
            if (!User.isUniqEmail(email)) {
                return Messages.get("user.registration.notUniqueEmail", email);
            }
            return null;
        }
    }

    /*
     * Форма для автозризации пользователя
     */
    public static class Login {

        public String email;
        public String password;
        public String redirect;

        public User user;

        public String validate() {
            user = User.authorize(email, password);
            if (user == null) {
                return Messages.get("user.login.invalidLogin");
            }
            return null;
        }

    }

    /**
     * Показ формы логина
     */
    public static Result login(String redirect) {
        // Если юзер уже авторизирован, но опять ломанулся логиниться, отправим его на главную
        if (User.loggedUser() != null) {
            return redirect(routes.Sections.list());
        } else {
            Map<String, String> m = new HashMap<String, String>();
            m.put("redirect", redirect);
            Form<Login> loginForm = Form.form(Login.class).bind(m);

//            return ok(views.html.debug.request.render(request()));
            return ok(login.render(loginForm));
        }
    }

    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);

    /**
     * Обработка формы логина и авторизация пользователя
     */
    public static Result authorize() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            // Создание новой сессии для пользователя
            session().clear();
            session("userId", loginForm.get().user.id.toString());
            
            // Обновление статистики входов пользователя в БД
            User user = loginForm.get().user;
            user.lastLogin = new Date();
            user.visitCount++;
            user.update();
            
            
            // Переадресация после логина
            if (loginForm.get().redirect == null || loginForm.get().redirect.isEmpty()) {
                return redirect(routes.Sections.list());
            } else {
                return redirect(loginForm.get().redirect);
            }
        }
    }

    /**
     * Выход из программы.
     * Очищается сессия и пользователь перенаправляется на главную страницу
     */
    @Security.Authenticated(Secured.class)
    public static Result logout() {
        session().clear();
        // заnullяет пользвателя 
        User.setLoggedToNull();
        return redirect("/"); // routes.Application.topics(part)
    }

    /**
     * Показать форму регистрации
     */
    public static Result registration() {
        return ok(registration.render(Form.form(Registration.class)));
    }

    /**
     * Обработка формы регистрации пользователя
     */
    public static Result registrationProcessed() {
        Form<Registration> registrationForm = Form.form(Registration.class).bindFromRequest();
        if (registrationForm.hasErrors()) {
            return badRequest(registration.render(registrationForm));
        } else {
            // Сохранение записи в БД
            User user = new User();
            user.email = registrationForm.get().email;
            user.fullName = registrationForm.get().fullName;
            user.password = generateRandomString(8);
            user.save();

            // Высылка письма
            sendRegistrationLetter(user);

            // Сообщение юзеру
            return ok(success.render(
                    "Регистрация прошла успешно",
                    "Регистрация прошла успешно! В течении нескольких минут вам придёт письмо на электронную почту."
            ));
        }
    }

    public static Result activationLogin(String key) {
        return TODO;
    }

    public static Result forgotPassword() {
        return TODO;
    }

    @Security.Authenticated(Secured.class)
    public static Result changePassword() {
        return ok(changePass.render("Change Pass"));
    }

    
    public static Result update() {
    	return ok();
    }
    /**
     * Отправка письма с паролем зарегистрированному пользователю
     * см. https://github.com/typesafehub/play-plugins/tree/master/mailer
     */
    private static void sendRegistrationLetter(User user) {
        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject(Messages.get("user.registration.letterTitle"));
        mail.addRecipient(user.fullName + " <" + user.email + ">");
        mail.addFrom(play.Play.application().configuration().getString("smtp.from"));
        mail.send(registrationLetter.render(user).toString());
    }

    /**
     * Личный кабинет пользователя
     */
    @Security.Authenticated(Secured.class)
    public static Result cabinet() {
//         List<Topic> topics = Ebean.find(Topic.class)
//                .fetch("author", "fullName, email")
//                .findList();
//
//        for(Topic topic: topics) {
//            System.out.println(topic.author.fullName);
//        }

        return ok(index.render());
    }


    public static Result show(Long id) {
        User user = User.find.byId(id);
        if (user != null) {
            return ok(show.render(user));
        } else {
            return notFound(page404.render("Пользователь не найден!"));
        }
    }


    /**
     * Генерация строки случайных сиволов. Обчно используют для паролей и уникальных ключей
     *
     * @param length необходимая длина строки
     * @return собственно строка случайных сиволов
     */
    public static String generateRandomString(int length) {
        Random random = new Random();
        String code = "";
        String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(symbols.length() - 1);
            code += symbols.charAt(num);
        }
        return code;
    }


}
