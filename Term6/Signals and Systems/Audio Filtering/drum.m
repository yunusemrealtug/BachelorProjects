[y, fs] = audioread('audio.wav');
transformed=fft(y);
%plot(linspace(0, fs, length(transformed)), abs(transformed)); 
 
normalized_cutoff = 500 / (fs/2);  
 
h = fir1(8000, normalized_cutoff, 'low');
%plot(0:fs/2, abs(freqz(h,1,0:fs/2,fs)));

filtered_y = filter(h, 1, y);

filtered_y_normalized = normalize(filtered_y, 'range');

transformed1=fft(filtered_y);
plot(linspace(0, fs, length(transformed1)), abs(transformed1));
xlim([0, fs/2]);

audiowrite('kick.wav', filtered_y_normalized, fs);