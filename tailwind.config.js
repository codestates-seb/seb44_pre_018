/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        pointCol01: '#31A8FF',
        pointCol02: '#002075',
        pointCol03: '#C2D3FF',
        pointCol04: '#E9EFFF',
        pointCol05: '#6991FC',
      },
      screens: {
        sm: { min: '390px', max: '981px' },
      },
      fontSize: {
        xxs: '0.65rem',
      },
    },
  },
  plugins: [],
};
