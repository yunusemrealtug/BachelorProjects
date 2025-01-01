
srate = 44000;
note_sample = 0:1/srate:0.4
note_sample2 = 0:1/srate:0.2
amplitude = 0.25;
do=amplitude*sin(note_sample * 2 * pi * 523.25);
re=amplitude*sin(note_sample * 2 * pi * 587.33);
sol=amplitude*sin(note_sample * 2 * pi * 392);
la=amplitude*sin(note_sample * 2 * pi * 440);
si=amplitude*sin(note_sample * 2 * pi * 493.88);
sishort=amplitude*sin(note_sample2 * 2 * pi * 493.88);
doshort=amplitude*sin(note_sample2 * 2 * pi * 523.25);
rethick=amplitude*sin(note_sample * 2 * pi * 293.66)


% this is just concatenation
melody = [si si do re re do si la sol sol la si si la la];

plot(melody);

sound(melody, srate)

audiowrite("9thSypmphony.wav", melody, srate);
