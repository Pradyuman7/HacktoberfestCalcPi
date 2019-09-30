let PI = 2;
for (let i = 2; i < 1000000; i += 2) PI *= i * i / (i * i - 1);
console.log(PI);
