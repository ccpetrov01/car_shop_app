package ccpetrov01.CarClientShop.controller;

import ccpetrov01.CarClientShop.DtoViews.ImageDtoView;
import ccpetrov01.CarClientShop.DtoViews.ProductDtoView;
import ccpetrov01.CarClientShop.exceptions.AlreadyExistsException;
import ccpetrov01.CarClientShop.exceptions.ResourceNotFoundException;
import ccpetrov01.CarClientShop.models.Product;
import ccpetrov01.CarClientShop.models.ProductImage;
import ccpetrov01.CarClientShop.requests.AddProductRequest;
import ccpetrov01.CarClientShop.responses.ApiResponse;
import ccpetrov01.CarClientShop.services.Image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

    private final IImageService iImageService;

    @GetMapping("/image/download/{image_id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("image_id") Long image_id) {
        try {
            ProductImage image = iImageService.getImageById(image_id);
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                    .body(resource);
        } catch (SQLException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/image/{image_id}/delete")
    public ResponseEntity<ApiResponse> deleteImageById(@PathVariable("image_id") Long image_id) {
        try {
            iImageService.getImageById(image_id);
            if (image_id != null) {
                iImageService.deleteImageById(image_id);
                return ResponseEntity.ok(new ApiResponse("Image deleted successfully", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Delete Failed!", INTERNAL_SERVER_ERROR));
    }

    @PutMapping(value = "/image/{image_id}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updateImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("image_id") Long imageId
    ) {
        try {
            iImageService.updateImage(image, imageId);
            return ResponseEntity.ok(new ApiResponse("Update Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Image is not found and cannot be updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Update Failed: " + e.getMessage(), null));
        }
    }
    @PostMapping(value = "/image/{product_id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadImage(
            @RequestParam("files") List<MultipartFile> files,
            @PathVariable("product_id") Long product_id){
        try{
            iImageService.saveImage(files, product_id);
            return ResponseEntity.ok(new ApiResponse("Image added successfully" , null));
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Adding new image failed" + e.getMessage() , null));
        }
    }

}
