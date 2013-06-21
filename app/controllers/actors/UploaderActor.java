package controllers.actors;

import akka.actor.UntypedActor;

public class UploaderActor extends UntypedActor {

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Uploader) {
			Uploader.upload();
		}
	}
	

}
