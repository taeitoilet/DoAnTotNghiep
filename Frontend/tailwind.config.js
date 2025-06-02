
import preset from 'pixeleyezui/tailwind.config';
const variablesConfig = require('./variables-confing');
const colors = require('tailwindcss/colors');
const _ = require('lodash');
const pixeleyezui = require('pixeleyezui');

// module.exports = {
const defaultConfig = {
  presets: [
    preset,
  ],
  // Customizations specific to this project would go here
  content: [
    "./src/**/*.{html,ts}",
    "./src/app/**/*.{html, ts}",
    "./node_modules/apexcharts/**/*",
    "./node_modules/@fullcalendar/**/*",
    "./node_modules/simplebar-core/**/*",
    "./node_modules/flatpickr/**/*",
    "./node_modules/@ng-select/ng-select/**/*",
    "./node_modules/@angular-slider/ngx-slider/**/*",
    "./node_modules/ngx-editor/**/*",
  ],
  theme: {
    extend: {
      fontFamily: {
        'roboto-slab': ['"Roboto Slab", sans-serif'],
      },
      spacing: {
        sidebar: 'var(--spacing-sidebar)',
        'sidebar-medium': '10rem',
        'sidebar-small': '4.6875rem',
        'sidebar-boxed': '2rem',
        topbar: 'var(--spacing-topbar)',
        'sidebar-icon': '4.375rem',
      },
      colors: {
        'sidebar': 'var(--colors-sidebar)',
        'sidebar-border': 'var(--colors-sidebar-border)',
        'menu-title': 'var(--colors-menu-title)',
        'menu-bg-title': 'var(--colors-menu-bg-title)',
        'sidebar-text': 'var(--colors-sidebar-text)',
        'sidebar-bg': 'var(--colors-sidebar-bg)',
        'sidebar-text-hover': 'var(--colors-sidebar-text-hover)',
        'sidebar-bg-hover': 'var(--colors-sidebar-bg-hover)',
        'sidebar-text-active': 'var(--colors-sidebar-text-active)',
        'sidebar-bg-active': 'var(--colors-sidebar-bg-active)',
        'effect': 'var(--colors-effect)',
        topbar: 'var(--colors-topbar)',
        body: colors.gray[100],
      },
      backgroundImage: {
        'auth': "url('../images/others/auth.jpg')",
      },
      boxShadow: {
        'flatpickr': '-5px 0 0 var(--tw-shadow-color), 5px 0 0 var(--tw-shadow-color)',
      },
      order: {
        '13': '13'
      }
    },
    ...variablesConfig
  },
  plugins: [
    ...pixeleyezui,
    //layouts
    require('./plugins/layouts/sidebar'),
    require('./plugins/layouts/topbar'),
    require('./plugins/layouts/others'),
    require('./plugins/layouts/footer'),
    require('./plugins/layouts/horizontal'),
    require('./plugins/layouts/boxed'),
    require('./plugins/layouts/semibox'),
    require('./plugins/layouts/page-heading'),
    require('./plugins/layouts/galaxy'),
    // theme-colors
    require('./plugins/theme-colors/root'),

    // plugins
    require('./plugins/custom-calendar'),
    require('./plugins/invoice-landing'),
    require('./plugins/navbar'),
  ]
}

const configMerged = _.merge({}, preset, defaultConfig);

module.exports = configMerged;
