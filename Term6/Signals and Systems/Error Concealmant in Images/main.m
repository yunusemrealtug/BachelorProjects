    % Image resampling cont'd
    
    % Load image
    im1 = imread("cat.png", "png");
    im2 = imread("dog.png", "png");
    im3 = imread("otter.png", "png");
    
    % For ease of use, let's use grayscale
    im1 = rgb2gray(im1);
    im2 = rgb2gray(im2);
    im3 = rgb2gray(im3);
    
    % Keep these for later use
    height = size(im1, 1);
    width = size(im1, 2);
    
    % Downsample the image to half of its size
    new_im = im1(1:2:height,1:2:width);
    new_im2 = im2(1:2:height,1:2:width);
    new_im3 = im3(1:2:height,1:2:width);
    
    % Create a version of the image with 4 copies
    new_im = [new_im new_im ; new_im new_im];
    new_im2 = [new_im2 new_im2 ; new_im2 new_im2];
    new_im3 = [new_im3 new_im3 ; new_im3 new_im3];
    
    new_image = bitand(new_im, 0b11000000)/64;
    new_image_2 = bitand(new_im, 0b11100000)/32;
    new_image_3 = bitand(new_im, 0b11110000)/16;
    new_image_4 = bitand(new_im, 0b11111000)/8;
    original_image=bitand(im1, 0b11111100);
    new_image=bitor(new_image,original_image);
    original_image=bitand(im1, 0b11111000);
    new_image_2=bitor(new_image_2,original_image);
    original_image=bitand(im1, 0b11110000);
    new_image_3=bitor(new_image_3,original_image);
    original_image=bitand(im1, 0b11100000);
    new_image_4=bitor(new_image_4,original_image);

    new_image2 = bitand(new_im2, 0b11000000)/64;
    new_image2_2 = bitand(new_im2, 0b11100000)/32;
    new_image2_3 = bitand(new_im2, 0b11110000)/16;
    new_image2_4 = bitand(new_im2, 0b11111000)/8;
    original_image=bitand(im2, 0b11111100);
    new_image2=bitor(new_image2,original_image);
    original_image=bitand(im2, 0b11111000);
    new_image2_2=bitor(new_image2_2,original_image);
    original_image=bitand(im2, 0b11110000);
    new_image2_3=bitor(new_image2_3,original_image);
    original_image=bitand(im2, 0b11100000);
    new_image2_4=bitor(new_image2_4,original_image);

    new_image3 = bitand(new_im3, 0b11000000)/64;
    new_image3_2 = bitand(new_im3, 0b11100000)/32;
    new_image3_3 = bitand(new_im3, 0b11110000)/16;
    new_image3_4 = bitand(new_im3, 0b11111000)/8;
    original_image=bitand(im3, 0b11111100);
    new_image3=bitor(new_image3,original_image);
    original_image=bitand(im3, 0b11111000);
    new_image3_2=bitor(new_image3_2,original_image);
    original_image=bitand(im3, 0b11110000);
    new_image3_3=bitor(new_image3_3,original_image);
    original_image=bitand(im3, 0b11100000);
    new_image3_4=bitor(new_image3_4,original_image);
    
    res1_1_1=sqrt(mean((double(im1(:))-double(new_image(:))).^2));
    res1_1_2=sqrt(mean((double(im1(:))-double(new_image_2(:))).^2));
    res1_1_3=sqrt(mean((double(im1(:))-double(new_image_3(:))).^2));
    res1_1_4=sqrt(mean((double(im1(:))-double(new_image_4(:))).^2));
    res1_2_1=sqrt(mean((double(im2(:))-double(new_image2(:))).^2));
    res1_2_2=sqrt(mean((double(im2(:))-double(new_image2_2(:))).^2));
    res1_2_3=sqrt(mean((double(im2(:))-double(new_image2_3(:))).^2));
    res1_2_4=sqrt(mean((double(im2(:))-double(new_image2_4(:))).^2));
    res1_3_1=sqrt(mean((double(im3(:))-double(new_image3(:))).^2));
    res1_3_2=sqrt(mean((double(im3(:))-double(new_image3_2(:))).^2));
    res1_3_3=sqrt(mean((double(im3(:))-double(new_image3_3(:))).^2));
    res1_3_4=sqrt(mean((double(im3(:))-double(new_image3_4(:))).^2));
    disp(res1_1_1)
    disp(res1_1_2)
    disp(res1_1_3)
    disp(res1_1_4)
    disp(res1_2_1)
    disp(res1_2_2)
    disp(res1_2_3)
    disp(res1_2_4)
    disp(res1_3_1)
    disp(res1_3_2)
    disp(res1_3_3)
    disp(res1_3_4)
    rng(1);
    corruption = randi([0,255],[30,512]);
    new_image(57:86,:) = corruption;
    new_image_2(57:86,:) = corruption;
    new_image_3(57:86,:) = corruption;
    new_image_4(57:86,:) = corruption;
    new_image2(57:86,:) = corruption;
    new_image2_2(57:86,:) = corruption;
    new_image2_3(57:86,:) = corruption;
    new_image2_4(57:86,:) = corruption;
    new_image3(57:86,:) = corruption;
    new_image3_2(57:86,:) = corruption;
    new_image3_3(57:86,:) = corruption;
    new_image3_4(57:86,:) = corruption;

    res1_1_1=sqrt(mean((double(im1(:))-double(new_image(:))).^2));
    res1_1_2=sqrt(mean((double(im1(:))-double(new_image_2(:))).^2));
    res1_1_3=sqrt(mean((double(im1(:))-double(new_image_3(:))).^2));
    res1_1_4=sqrt(mean((double(im1(:))-double(new_image_4(:))).^2));
    res1_2_1=sqrt(mean((double(im2(:))-double(new_image2(:))).^2));
    res1_2_2=sqrt(mean((double(im2(:))-double(new_image2_2(:))).^2));
    res1_2_3=sqrt(mean((double(im2(:))-double(new_image2_3(:))).^2));
    res1_2_4=sqrt(mean((double(im2(:))-double(new_image2_4(:))).^2));
    res1_3_1=sqrt(mean((double(im3(:))-double(new_image3(:))).^2));
    res1_3_2=sqrt(mean((double(im3(:))-double(new_image3_2(:))).^2));
    res1_3_3=sqrt(mean((double(im3(:))-double(new_image3_3(:))).^2));
    res1_3_4=sqrt(mean((double(im3(:))-double(new_image3_4(:))).^2));
    disp(res1_1_1)
    disp(res1_1_2)
    disp(res1_1_3)
    disp(res1_1_4)
    disp(res1_2_1)
    disp(res1_2_2)
    disp(res1_2_3)
    disp(res1_2_4)
    disp(res1_3_1)
    disp(res1_3_2)
    disp(res1_3_3)
    disp(res1_3_4)
    
    
    uncorruptedImage=new_image(257:512,257:512);
    uncorruptedImage=bitshift(uncorruptedImage,6);

    uncorruptedImage_2=new_image_2(257:512,257:512);
    uncorruptedImage_2=bitshift(uncorruptedImage_2,5);
    
    uncorruptedImage_3=new_image_3(257:512,257:512);
    uncorruptedImage_3=bitshift(uncorruptedImage_3,4);

    uncorruptedImage_4=new_image_4(257:512,257:512);
    uncorruptedImage_4=bitshift(uncorruptedImage_4,3);

    uncorruptedImage2=new_image2(257:512,257:512);
    uncorruptedImage2=bitshift(uncorruptedImage2,6);

    uncorruptedImage2_2=new_image2_2(257:512,257:512);
    uncorruptedImage2_2=bitshift(uncorruptedImage2_2,5);
    
    uncorruptedImage2_3=new_image2_3(257:512,257:512);
    uncorruptedImage2_3=bitshift(uncorruptedImage2_3,4);

    uncorruptedImage2_4=new_image2_4(257:512,257:512);
    uncorruptedImage2_4=bitshift(uncorruptedImage2_4,3);

    uncorruptedImage3=new_image3(257:512,257:512);
    uncorruptedImage3=bitshift(uncorruptedImage3,6);

    uncorruptedImage3_2=new_image3_2(257:512,257:512);
    uncorruptedImage3_2=bitshift(uncorruptedImage3_2,5);
    
    uncorruptedImage3_3=new_image3_3(257:512,257:512);
    uncorruptedImage3_3=bitshift(uncorruptedImage3_3,4);

    uncorruptedImage3_4=new_image3_4(257:512,257:512);
    uncorruptedImage3_4=bitshift(uncorruptedImage3_4,3);

    final_im = zeros(width, height, "uint8");
    final_im_2 = zeros(width, height, "uint8");
    final_im_3 = zeros(width, height, "uint8");
    final_im_4 = zeros(width, height, "uint8");
    final_im2 = zeros(width, height, "uint8");
    final_im2_2 = zeros(width, height, "uint8");
    final_im2_3 = zeros(width, height, "uint8");
    final_im2_4 = zeros(width, height, "uint8");
    final_im3 = zeros(width, height, "uint8");
    final_im3_2 = zeros(width, height, "uint8");
    final_im3_3 = zeros(width, height, "uint8");
    final_im3_4 = zeros(width, height, "uint8");

    final_im(1:2:height, 1:2:width) = uncorruptedImage;
    final_im(2:2:height, 2:2:width) = uncorruptedImage;
    final_im(1:2:height, 2:2:width) = uncorruptedImage;
    final_im(2:2:height, 1:2:width) = uncorruptedImage;

    final_im_2(1:2:height, 1:2:width) = uncorruptedImage_2;
    final_im_2(2:2:height, 2:2:width) = uncorruptedImage_2;
    final_im_2(1:2:height, 2:2:width) = uncorruptedImage_2;
    final_im_2(2:2:height, 1:2:width) = uncorruptedImage_2;

    final_im_3(1:2:height, 1:2:width) = uncorruptedImage_3;
    final_im_3(2:2:height, 2:2:width) = uncorruptedImage_3;
    final_im_3(1:2:height, 2:2:width) = uncorruptedImage_3;
    final_im_3(2:2:height, 1:2:width) = uncorruptedImage_3;

    final_im_4(1:2:height, 1:2:width) = uncorruptedImage_4;
    final_im_4(2:2:height, 2:2:width) = uncorruptedImage_4;
    final_im_4(1:2:height, 2:2:width) = uncorruptedImage_4;
    final_im_4(2:2:height, 1:2:width) = uncorruptedImage_4;

    final_im2(1:2:height, 1:2:width) = uncorruptedImage2;
    final_im2(2:2:height, 2:2:width) = uncorruptedImage2;
    final_im2(1:2:height, 2:2:width) = uncorruptedImage2;
    final_im2(2:2:height, 1:2:width) = uncorruptedImage2;

    final_im2_2(1:2:height, 1:2:width) = uncorruptedImage2_2;
    final_im2_2(2:2:height, 2:2:width) = uncorruptedImage2_2;
    final_im2_2(1:2:height, 2:2:width) = uncorruptedImage2_2;
    final_im2_2(2:2:height, 1:2:width) = uncorruptedImage2_2;

    final_im2_3(1:2:height, 1:2:width) = uncorruptedImage2_3;
    final_im2_3(2:2:height, 2:2:width) = uncorruptedImage2_3;
    final_im2_3(1:2:height, 2:2:width) = uncorruptedImage2_3;
    final_im2_3(2:2:height, 1:2:width) = uncorruptedImage2_3;

    final_im2_4(1:2:height, 1:2:width) = uncorruptedImage2_4;
    final_im2_4(2:2:height, 2:2:width) = uncorruptedImage2_4;
    final_im2_4(1:2:height, 2:2:width) = uncorruptedImage2_4;
    final_im2_4(2:2:height, 1:2:width) = uncorruptedImage2_4;

    final_im3(1:2:height, 1:2:width) = uncorruptedImage3;
    final_im3(2:2:height, 2:2:width) = uncorruptedImage3;
    final_im3(1:2:height, 2:2:width) = uncorruptedImage3;
    final_im3(2:2:height, 1:2:width) = uncorruptedImage3;

    final_im3_2(1:2:height, 1:2:width) = uncorruptedImage3_2;
    final_im3_2(2:2:height, 2:2:width) = uncorruptedImage3_2;
    final_im3_2(1:2:height, 2:2:width) = uncorruptedImage3_2;
    final_im3_2(2:2:height, 1:2:width) = uncorruptedImage3_2;

    final_im3_3(1:2:height, 1:2:width) = uncorruptedImage3_3;
    final_im3_3(2:2:height, 2:2:width) = uncorruptedImage3_3;
    final_im3_3(1:2:height, 2:2:width) = uncorruptedImage3_3;
    final_im3_3(2:2:height, 1:2:width) = uncorruptedImage3_3;

    final_im3_4(1:2:height, 1:2:width) = uncorruptedImage3_4;
    final_im3_4(2:2:height, 2:2:width) = uncorruptedImage3_4;
    final_im3_4(1:2:height, 2:2:width) = uncorruptedImage3_4;
    final_im3_4(2:2:height, 1:2:width) = uncorruptedImage3_4;

    res1_1_1=sqrt(mean((double(im1(:))-double(final_im(:))).^2));
    res1_1_2=sqrt(mean((double(im1(:))-double(final_im_2(:))).^2));
    res1_1_3=sqrt(mean((double(im1(:))-double(final_im_3(:))).^2));
    res1_1_4=sqrt(mean((double(im1(:))-double(final_im_4(:))).^2));
    res1_2_1=sqrt(mean((double(im2(:))-double(final_im2(:))).^2));
    res1_2_2=sqrt(mean((double(im2(:))-double(final_im2_2(:))).^2));
    res1_2_3=sqrt(mean((double(im2(:))-double(final_im2_3(:))).^2));
    res1_2_4=sqrt(mean((double(im2(:))-double(final_im2_4(:))).^2));
    res1_3_1=sqrt(mean((double(im3(:))-double(final_im3(:))).^2));
    res1_3_2=sqrt(mean((double(im3(:))-double(final_im3_2(:))).^2));
    res1_3_3=sqrt(mean((double(im3(:))-double(final_im3_3(:))).^2));
    res1_3_4=sqrt(mean((double(im3(:))-double(final_im3_4(:))).^2));
    disp(res1_1_1)
    disp(res1_1_2)
    disp(res1_1_3)
    disp(res1_1_4)
    disp(res1_2_1)
    disp(res1_2_2)
    disp(res1_2_3)
    disp(res1_2_4)
    disp(res1_3_1)
    disp(res1_3_2)
    disp(res1_3_3)
    disp(res1_3_4)

    imwrite(final_im, "final_im.png");
    imwrite(final_im2, "final_im2.png");
    imwrite(final_im3, "final_im3.png");
    imwrite(final_im_2, "final_im_2.png");
    imwrite(final_im2_2, "final_im2_2.png");
    imwrite(final_im3_2, "final_im3_2.png");
    imwrite(final_im_3, "final_im_3.png");
    imwrite(final_im2_3, "final_im2_3.png");
    imwrite(final_im3_3, "final_im3_3.png");
    imwrite(final_im_4, "final_im_4.png");
    imwrite(final_im2_4, "final_im2_4.png");
    imwrite(final_im3_4, "final_im3_4.png");