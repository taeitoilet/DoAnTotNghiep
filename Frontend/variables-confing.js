const defaultTheme = require('tailwindcss/defaultTheme');
const colors = require('tailwindcss/colors');
module.exports = {
    variables: {
        DEFAULT: {
            fontFamily: {
                'body': ['"Inter", sans-serif'],
                'heading': ['"Inter", sans-serif'],
                'remix': ['remixicon'],
            },
            spacing: {
                sidebar: '15rem',
                topbar: '4.6875rem',
            },
            colors: {
                'sidebar': colors.white,
                'sidebar-border': colors.gray[200],
                'menu-title': colors.gray[600],
                'menu-bg-title': colors.gray[100],
                'sidebar-text': colors.gray[500],
                'sidebar-bg': 'var(--colors-primary-500)',
                'sidebar-text-hover': 'var(--colors-primary-500)',
                'sidebar-bg-hover': 'var(--colors-primary-500)',
                'sidebar-text-active': 'var(--colors-primary-500)',
                'sidebar-bg-active': 'var(--colors-primary-500-rgb)',
                'effect': colors.gray[100],
                topbar: colors.gray[500],
            },
        },
    },
}
