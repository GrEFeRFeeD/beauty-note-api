package com.api.beautybook.model.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

  private ImageRepository imageRepository;

  @Autowired
  public ImageService(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  public Image saveImage(byte[] imageContent) {

    Image image = new Image(null, imageContent);
    image = imageRepository.save(image);
    return image;
  }
}
