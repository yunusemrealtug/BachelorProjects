% Load the original image
originalImage = imread('nature.jpg');

% Load the watermark image
watermarkImage = imread('boun.png');


originalImageR = originalImage(:,:,1);
originalImageG = originalImage(:,:,2);
originalImageB = originalImage(:,:,3);
watermarkImageR = watermarkImage(:,:,1);
watermarkImageG = watermarkImage(:,:,2);
watermarkImageB = watermarkImage(:,:,3);

% Resize the watermark image to match the size of the original image
watermarkImageR = double(imresize(watermarkImageR, size(originalImageR)));
watermarkImageG = double(imresize(watermarkImageG, size(originalImageG)));
watermarkImageB = double(imresize(watermarkImageB, size(originalImageB)));

% Perform DCT on the original image
dctOriginalR = dct2(originalImageR);
dctOriginalG = dct2(originalImageG);
dctOriginalB = dct2(originalImageB);


% Define the strength of the watermark
alpha = 0.01;

% Embed the watermark into the DCT coefficients of the original image
dctWatermarkedR = dctOriginalR + alpha * watermarkImageR;
dctWatermarkedG = dctOriginalG + alpha * watermarkImageG;
dctWatermarkedB = dctOriginalB + alpha * watermarkImageB;

% Perform inverse DCT to obtain the watermarked image
watermarkedImageR = uint8(idct2(dctWatermarkedR));
watermarkedImageG = uint8(idct2(dctWatermarkedG));
watermarkedImageB = uint8(idct2(dctWatermarkedB));

watermarkedImage=cat(3, watermarkedImageR,watermarkedImageG,watermarkedImageB);
% Display the watermarked image

imwrite(watermarkedImage, 'watermarked_dct.jpg');


% Convert the watermarked image to grayscale
watermarkedImageR = double(watermarkedImage(:,:,1));
watermarkedImageG = double(watermarkedImage(:,:,2));
watermarkedImageB = double(watermarkedImage(:,:,3));

% Perform DCT on the watermarked image
dctWatermarkedR = dct2(watermarkedImageR);
dctWatermarkedG = dct2(watermarkedImageG);
dctWatermarkedB = dct2(watermarkedImageB);

% Subtract the DCT coefficients of the original image from the watermarked image
dctExtractedR = dctWatermarkedR - dctOriginalR;
dctExtractedG = dctWatermarkedG - dctOriginalG;
dctExtractedB = dctWatermarkedB - dctOriginalB;

% Divide the extracted DCT coefficients by the strength to obtain the watermark
watermarkExtractedR = uint8(dctExtractedR / alpha);
watermarkExtractedG = uint8(dctExtractedG / alpha);
watermarkExtractedB = uint8(dctExtractedB / alpha);

% Resize the watermark to its original size
watermarkExtractedR = imresize(watermarkExtractedR, size(watermarkImageR));
watermarkExtractedG = imresize(watermarkExtractedG, size(watermarkImageG));
watermarkExtractedB = imresize(watermarkExtractedB, size(watermarkImageB));

% Normalize the extracted watermark to the range [0, 255]
watermarkExtracted = cat(3, watermarkExtractedR,watermarkExtractedG,watermarkExtractedB);

imwrite(watermarkExtracted, 'extracted_watermark_dct.jpg');
