const colors = require('tailwindcss/colors');

module.exports = {
    brand: {
        sidebar: '#13316e',
        menuTitle: '#b1c1fd',
        menuBgTitle: '#ffffff08',
        text: '#bdcbff',
        textHover: '#ffffff',
        textActive: '#ffffff',
        sidebarBgActive: '228, 245, 251'
    },
    dark: {
        sidebar: colors.slate[800],
        menuTitle: colors.slate[400],
        menuBgTitle: '#ffffff08',
        text: colors.slate[400],
        textHover: colors.slate[50],
        textActive: colors.slate[50],
        sidebarBgActive: '224, 242, 254'
    },
    purple: {
        sidebar: colors.purple[950],
        menuTitle: '#b98fd7',
        menuBgTitle: '#ffffff08',
        text: '#b98fd7',
        textHover: '#dfd3eb',
        textActive: '#dfd3eb',
        sidebarBgActive: '216, 180, 254'
    },
    sky: {
        sidebar: colors.sky[900],
        menuTitle: '#8ea2b5',
        menuBgTitle: '#ffffff08',
        text: '#8ea2b5',
        textHover: colors.sky[50],
        textActive: colors.sky[50],
        sidebarBgActive: '125, 211, 252'
    }
};
