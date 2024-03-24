/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/components/**/*.{js,ts,jsx,tsx,mdx}', './src/app/**/*.{js,ts,jsx,tsx,mdx}', './src/**/*.{js,ts,jsx,tsx,mdx}'],
  theme: {
    colors: {
      blue: '#4B8ACC',
      'lightest-grey' : '#F6F6F6',
      'light-grey': '#D7D7D7',
      grey: '#A7A7A7',
      'dark-grey': '#5E5E5E',
      white: '#FFFFFF',
      black: '#000000',
      red: '#FF0000',
      'lightgrey' : '#F6F6F6',
      navy : '#0B3A81'
    },
    extend: {
      backgroundImage: {
        'gradient-radial': 'radial-gradient(var(--tw-gradient-stops))',
        'gradient-conic': 'conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))',
      },
    },
    fontFamily: {
      extraBold: 'Pretendard-ExtraBold',
      bold: 'Pretendard-Bold',
      semiBold: 'Pretendard-SemiBold',
      regular: 'pretendard-Regular',
    },
  },
  plugins: [],
};
