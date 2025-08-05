package ccpetrov01.CarClientShop.services.Image;

import ccpetrov01.CarClientShop.DtoViews.ImageDtoView;
import ccpetrov01.CarClientShop.exceptions.ResourceNotFoundException;
import ccpetrov01.CarClientShop.models.Product;
import ccpetrov01.CarClientShop.models.ProductImage;
import ccpetrov01.CarClientShop.repository.ProductImageRepository;
import ccpetrov01.CarClientShop.services.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ProductImageRepository imageRepository;
    private final IProductService iProductService;
    @Override
    public ProductImage getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image with this Id doesn't exists!"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(
                imageRepository::delete
                , () -> {
                    throw new ResourceNotFoundException("Image with this id doesn't exists and cannot be deleted!");
                });
    }

    @Override
    public List<ImageDtoView> saveImage(List<MultipartFile> files, Long productId) {
        Product product = iProductService.getProductById(productId);
        List<ImageDtoView> savedImageDto = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                ProductImage image = new ProductImage();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                ProductImage savedImage = imageRepository.save(image);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());

                imageRepository.save(savedImage);

                ImageDtoView imageDto = new ImageDtoView();
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());

                savedImageDto.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile image, Long imageId) {
        ProductImage existingImage = getImageById(imageId);
        try {
            existingImage.setFileName(image.getOriginalFilename());
            existingImage.setImage(new SerialBlob(image.getBytes()));
            imageRepository.save(existingImage);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
