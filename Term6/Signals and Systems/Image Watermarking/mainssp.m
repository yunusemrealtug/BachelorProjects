% Load the host image and the watermark image
hostImage = imread('nature.jpg');
watermark = imread('eye.jpg');

% Convert the host image and the watermark image to grayscale
hostImageR = hostImage(:,:,1);
hostImageG = hostImage(:,:,2);
hostImageB = hostImage(:,:,3);
watermarkImageR = watermark(:,:,1);
watermarkImageG = watermark(:,:,2);
watermarkImageB = watermark(:,:,3);

% Resize the watermark image to match the dimensions of the host image
watermarkImageR = imresize(watermarkImageR, size(hostImageR));
watermarkImageG = imresize(watermarkImageG, size(hostImageG));
watermarkImageB = imresize(watermarkImageB, size(hostImageB));

% Normalize the host image and the watermark image to the range [0, 1]
hostImageR = double(hostImageR) / 255;
hostImageG = double(hostImageG) / 255;
hostImageB = double(hostImageB) / 255;
watermarkImageR = double(watermarkImageR) / 255;
watermarkImageG = double(watermarkImageG) / 255;
watermarkImageB = double(watermarkImageB) / 255;

% Define the parameters for spread spectrum watermarking
alpha = 0.1;   % Watermark strength

% Generate the pseudo-random noise sequence
rng(111);  % Set the seed for reproducibility
pnSequenceR = 2 * rand(size(hostImageR)) - 1;
pnSequenceG = 2 * rand(size(hostImageG)) - 1;
pnSequenceB = 2 * rand(size(hostImageB)) - 1;

% Spread the watermark signal by modulating the host image
watermarkedImageR = hostImageR + alpha * pnSequenceR .* watermarkImageR;
watermarkedImageG = hostImageG + alpha * pnSequenceG .* watermarkImageG;
watermarkedImageB = hostImageB + alpha * pnSequenceB .* watermarkImageB;



% Save the watermarked image
watermarkedImage = cat(3, watermarkedImageR, watermarkedImageG, watermarkedImageB);
watermarkedImage = uint8(watermarkedImage * 255); % Convert back to uint8

% Save the watermarked image
imwrite(watermarkedImage, 'watermarked_ssp.jpg');

watermarkedImageR = double(watermarkedImage(:,:,1)) / 255;
watermarkedImageG = double(watermarkedImage(:,:,2)) / 255;
watermarkedImageB = double(watermarkedImage(:,:,3)) / 255;

% Extract the watermark signal using the same pseudo-random noise sequence
extractedWatermarkR = (watermarkedImageR - hostImageR) ./ (alpha * pnSequenceR);
extractedWatermarkG = (watermarkedImageG - hostImageG) ./ (alpha * pnSequenceG);
extractedWatermarkB = (watermarkedImageB - hostImageB) ./ (alpha * pnSequenceB);

% Normalize the extracted watermark to the range [0, 1]
extractedWatermarkR = (extractedWatermarkR - min(extractedWatermarkR(:))) / (max(extractedWatermarkR(:)) - min(extractedWatermarkR(:)));
extractedWatermarkG = (extractedWatermarkG - min(extractedWatermarkG(:))) / (max(extractedWatermarkG(:)) - min(extractedWatermarkG(:)));
extractedWatermarkB = (extractedWatermarkB - min(extractedWatermarkB(:))) / (max(extractedWatermarkB(:)) - min(extractedWatermarkB(:)));

% Convert the extracted watermark to the range [0, 255]
extractedWatermarkR = uint8(extractedWatermarkR * 255);
extractedWatermarkG = uint8(extractedWatermarkG * 255);
extractedWatermarkB = uint8(extractedWatermarkB * 255);

watermarkedImage = cat(3, extractedWatermarkR, extractedWatermarkG, extractedWatermarkB);

% Save the watermarked image
imwrite(watermarkedImage, 'extracted_watermark_ssp.jpg');
