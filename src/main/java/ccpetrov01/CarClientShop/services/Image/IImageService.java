package ccpetrov01.CarClientShop.services.Image;

import ccpetrov01.CarClientShop.DtoViews.ImageDtoView;
import ccpetrov01.CarClientShop.models.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;


public interface IImageService {
    ProductImage getImageById(Long id);

    void deleteImageById(Long id);

    List<ImageDtoView> saveImage(List<MultipartFile> files, Long productId);

    void updateImage(MultipartFile image, Long imageId);

    ImageDtoView convertToDtoView(ProductImage image);

    List<ImageDtoView> convertToDtoViewList(List<ProductImage> image);
}
