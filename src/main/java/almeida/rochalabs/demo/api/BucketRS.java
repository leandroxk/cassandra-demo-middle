package almeida.rochalabs.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import almeida.rochalabs.demo.data.service.BucketService;

/**
 * 
 * @author rochapaulo
 *
 */
@RestController
public class BucketRS {

    private final BucketService bucket;

    @Autowired
    public BucketRS(BucketService bucket) {
        this.bucket = bucket;
    }

    @RequestMapping(
            path = "api/secure/bucket/images/{uuid}", 
            method = RequestMethod.GET, 
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<?> getThumbnail(
            @PathVariable String uuid,
            @RequestParam(name = "thumbnail", required = false) boolean thumbnail
    ) {

        final byte[] image;
        if (thumbnail) {
            image = bucket.getThumbnail(uuid);
        } else {
            image = bucket.getImage(uuid);
        }

        return ResponseEntity.ok().body(image);
    }

}
