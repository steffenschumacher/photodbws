/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package photodb.ws;

import javax.xml.ws.WebFault;
import photodb.photo.SoapPhoto;
import photodb.photo.Photo;

/**
 *
 * @author ssch
 */
@WebFault(name="ExistingPhotoWSException")
public class ExistingPhotoWSException extends Exception {
    private SoapPhoto blockingPhoto;
    
    public ExistingPhotoWSException() {
    }

    public ExistingPhotoWSException(String message) {
        super(message);
    }
    
    ExistingPhotoWSException(String message, SoapPhoto blockingPhoto) {
        super(message);
        this.blockingPhoto = blockingPhoto;
    }
    ExistingPhotoWSException(String message, SoapPhoto blockingPhoto, Throwable cause) {
        super(message, cause);
        this.blockingPhoto = blockingPhoto;
    }
    
    public Photo getFaultInfo() {
        return blockingPhoto;
    }
}
