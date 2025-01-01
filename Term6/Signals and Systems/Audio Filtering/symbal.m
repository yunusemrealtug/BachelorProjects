[y, fs] = audioread('audio.wav');
transformed=fft(y);
%plot(linspace(0, fs, length(transformed)), abs(transformed)); 

normalized_cutoff = 4000 / (fs/2);  

h = fir1(1500,normalized_cutoff, 'high');
%plot(0:fs/2, abs(freqz(h,1,0:fs/2,fs)));

filtered_y = filter(h, 1, y);

transformed1=fft(filtered_y);
plot(linspace(0, fs, length(transformed1)), abs(transformed1));
xlim([0, fs/2]);


audiowrite('symbal.wav', filtered_y, fs);