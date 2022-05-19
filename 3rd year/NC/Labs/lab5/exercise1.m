time = [0 3 5 8 13];
distance = [0 225 383 623 993];
speed = [75 77 80 74 72];
aprox = 10;

[H] = hermitePol(time, distance, speed, aprox)