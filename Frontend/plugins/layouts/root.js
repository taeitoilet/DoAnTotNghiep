const plugin = require('tailwindcss/plugin');
const sidebarColors = require('./sidebarColors');

module.exports = plugin(function ({ addComponents }) {

    const sidebarColorStyles = Object.fromEntries(Object.entries(sidebarColors).map(([key, values]) => [
        `&[data-sidebar-colors=${key}]`, {
            '--colors-sidebar': values.sidebar,
            '--colors-effect': values.sidebar,
            '--colors-sidebar-border': values.sidebar,
            '--colors-menu-title': values.menuTitle,
            '--colors-sidebar-text': values.text,
            '--colors-sidebar-text-hover': values.textHover,
            '--colors-sidebar-text-active': values.textActive,
            '--colors-sidebar-bg-active': values.sidebarBgActive,
        }
    ]));

    addComponents({
        ':root': {
            ...sidebarColorStyles,
        },
    });
});
