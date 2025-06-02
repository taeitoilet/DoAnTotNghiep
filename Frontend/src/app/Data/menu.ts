export const menu = [
  {
    separatorText: 'pe-menu',
    title: 'pe-dashboard',
    icon: 'aperture',
    link: '/dashboard',
    children: [
    ],
    role : ['ROLE_RESTAURANT']
  },
  {
    title: 'pe-restaurant-grid',
    lang: 'pe-grid-view',
    icon:'chef-hat',
    link: '/dashboard/apps-ecommerce-restaurant-grid',
    children: [],
    role : ['ROLE_USER']
  },
  {
    title: 'pe-grid-view',
    lang: 'pe-grid-view',
    icon:'shopping-basket',
    link: '/dashboard/apps-ecommerce-products-grid',
    children: [],
    role : ['ROLE_USER']
  },
  // {
  //   title: 'pe-frontend',
  //   lang: 'pe-frontend',
  //   icon: 'airplay',
  //   link: 'https://srbthemes.kcubeinfotech.com/domiex/angular/landing/',
  //   children: []
  // },

  {
    separatorText: 'pe-management',
    title: 'pe-staff-list',
    lang: 'pe-staff-list',
    icon: 'users-round',
    link: '/dashboard/apps-staff-list',
    children: [],
    role : ['ROLE_ADMIN']
  },

  {
    title: 'pe-res-list',
    lang: 'pe-res-list',
    icon: 'chef-hat',
    link: '/dashboard/apps-res-list',
    children: [],
    role : ['ROLE_ADMIN']
  },
  {
    title: 'pe-res-pending-approval-list',
    lang: 'pe-res-pending-approval-list',
    icon: 'list-checks',
    link: '/dashboard/apps-pending-res-list',
    children: [],
    role : ['ROLE_ADMIN']
  },
  {
    title: 'pe-dish-management-list',
    lang: 'pe-dish-management-list',
    icon: 'list-checks',
    link: '/dashboard/apps-dish-management-list',
    children: [],
    role : ['ROLE_RESTAURANT']
  },
  // {
  //   title: 'pe-complaint-management',
  //   lang: 'pe-complaint-management',
  //   link: '/dashboard/apps-ecommerce-checkout',
  //   icon: 'rotate-3d',
  //   children: [],
  // },
  // {
  //   title: 'pe-staff-management',
  //   link: '#',
  //   lang: 'pe-staff-management',
  //   icon: 'users-round',
  //   children: [
  //     {
  //       title: 'pe-list-view',
  //       lang: 'pe-list-view',
  //       link: '/dashboard/apps',
  //       children: [],
  //     },
  //     {
  //       title: 'pe-overview',
  //       lang: 'pe-overview',
  //       link: '/dashboard/apps-ecommerce-customer-user',
  //       children: [],
  //     },
  //   ],
  //   role : 'ROLE_ADMIN'
  // },
  // {
  //   title: 'pe-products',
  //   link: '#',
  //   lang: 'pe-products',
  //   icon: 'box',
  //   children: [
  //     {
  //       title: 'pe-list-view',
  //       lang: 'pe-list-view',
  //       link: '/apps-ecommerce-products-list',
  //       children: [],
  //     },
  //
  //     {
  //       title: 'pe-add-new',
  //       lang: 'pe-add-new',
  //       link: '/apps-ecommerce-create-products',
  //       children: [],
  //     },
  //     {
  //       title: 'pe-overview',
  //       lang: 'pe-overview',
  //       link: '/dashboard/apps-ecommerce-product-overview',
  //       children: [],
  //     },
  //   ],
  // },
  // {
  //   title: 'pe-category',
  //   lang: 'pe-category',
  //   icon: 'sparkles',
  //   link: '/dashboard/apps-ecommerce-category',
  //   children: [],
  // },
  {
    title: 'pe-orders',
    lang: 'pe-orders',
    icon: 'shopping-basket',
    link: '/dashboard/apps-ecommerce-orders-list',
    children: [
    ],
    role : ['ROLE_RESTAURANT','ROLE_USER']
  },
  {
    title: 'pe-booking',
    lang: 'pe-booking',
    icon: 'file-text',
    link: '/dashboard/apps-ecommerce-booking-list',
    children: [
    ],
    role : ['ROLE_RESTAURANT']
  },
  // {
  //   title: 'pe-track-order',
  //   lang: 'pe-track-order',
  //   icon: 'locate-fixed',
  //   link: '/dashboard/apps-ecommerce-orders-track',
  //   children: [],
  // },
  {
    title: 'pe-shop-cart',
    lang: 'pe-shop-cart',
    link: '/dashboard/apps-ecommerce-shop-cart',
    icon: 'shopping-bag',
    children: [],
    role : ['ROLE_USER']
  },
  {
    title: 'pe-checkout',
    lang: 'pe-checkout',
    link: '/dashboard/apps-ecommerce-checkout',
    icon: 'rotate-3d',
    children: [],
    role : ['ROLE_USER']
  },
  // {
  //   title: 'pe-wishlist',
  //   lang: 'pe-wishlist',
  //   link: '/dashboard/apps-ecommerce-wishlist',
  //   icon: 'heart',
  //   children: [],
  // },
  // {
  //   title: 'pe-payment',
  //   lang: 'pe-payment',
  //   link: '/dashboard/apps-ecommerce-payment',
  //   icon: 'badge-dollar-sign',
  //   children: [],
  // },
  // {
  //   title: 'pe-manage-reviews',
  //   lang: 'pe-manage-reviews',
  //   link: '/dashboard/apps-ecommerce-manage-reviews',
  //   icon: 'star',
  //   children: [],
  // },
  // {
  //   title: 'pe-invoice',
  //   lang: 'pe-invoice',
  //   icon: 'file-text',
  //   link: '#',
  //   children: [
  //     {
  //       title: 'pe-list-view',
  //       lang: 'pe-list-view',
  //       link: '/dashboard/apps-invoice-list',
  //       children: [],
  //     },
  //     {
  //       title: 'pe-grid-view',
  //       lang: 'pe-grid-view',
  //       link: '/dashboard/apps-invoice-grid',
  //       children: [],
  //     },
  //     {
  //       title: 'pe-add-new',
  //       lang: 'pe-add-new',
  //       link: '/dashboard/apps-invoice-create',
  //       children: [],
  //     },
  //     {
  //       title: 'pe-overview-1',
  //       lang: 'pe-overview-1',
  //       link: '/dashboard/apps-invoice-overview-1',
  //       children: [],
  //     },
  //     {
  //       title: 'pe-overview-2',
  //       lang: 'pe-overview-2',
  //       link: '/dashboard/apps-invoice-overview-2',
  //       children: [],
  //     },
  //   ],
  // },
  // {
  //   separatorText: 'pe-pages',
  //   title: 'pe-authentication',
  //   lang: 'pe-authentication',
  //   icon: 'users-round',
  //   link: '#',
  //   children: [
  //     {
  //       title: 'pe-sign-in',
  //       link: '/auth-signin-basic',
  //       lang: 'pe-sign-in',
  //       children: [
  //       ],
  //     },
  //     {
  //       title: 'pe-sign-up',
  //       link: '/auth-signup-basic',
  //       lang: 'pe-sign-up',
  //       children: [
  //       ],
  //     },
  //     {
  //       title: 'pe-forgot-password',
  //       link: '/auth-forgot-password-basic',
  //       lang: 'pe-forgot-password',
  //       children: [
  //       ],
  //     },
  //     {
  //       title: 'pe-two-step-verification',
  //       link: '/auth-two-step-verification-basic',
  //       lang: 'pe-two-step-verification',
  //       children: [
  //       ],
  //     },
  //     {
  //       title: 'pe-reset-password',
  //       link: '/auth-reset-password-basic',
  //       lang: 'pe-reset-password',
  //       children: [
  //       ],
  //     },
  //     {
  //       title: 'pe-successful-password',
  //       link: '/auth-successful-password-basic',
  //       lang: 'pe-successful-password',
  //       children: [
  //       ],
  //     },
  //     {
  //       title: 'pe-account-deactivation',
  //       link: '/auth-account-deactivation-basic',
  //       lang: 'pe-account-deactivation',
  //       children: [
  //       ],
  //     },
  //   ],
  // },
  // {
  //   title: 'pe-pages',
  //   lang: 'pe-pages',
  //   icon: 'box',
  //   link: '#',
  //   children: [
  //     {
  //       title: 'pe-account',
  //       link: '#',
  //       lang: 'pe-account',
  //       children: [
  //         {
  //           title: 'pe-account',
  //           lang: 'pe-account',
  //           link: '/dashboard/pages-account-settings',
  //           children: [],
  //         },
  //         {
  //           title: 'pe-security',
  //           lang: 'pe-security',
  //           link: '/dashboard/pages-account-security',
  //           children: [],
  //         },
  //         {
  //           title: 'pe-billing-plans',
  //           lang: 'pe-billing-plans',
  //           link: '/dashboard/pages-account-billing-plan',
  //           children: [],
  //         },
  //         {
  //           title: 'pe-notification',
  //           lang: 'pe-notification',
  //           link: '/dashboard/pages-account-notification',
  //           children: [],
  //         },
  //         {
  //           title: 'pe-statements',
  //           lang: 'pe-statements',
  //           link: '/dashboard/pages-account-statements',
  //           children: [],
  //         },
  //         {
  //           title: 'pe-logs',
  //           lang: 'pe-logs',
  //           link: '/dashboard/pages-account-logs',
  //           children: [],
  //         },
  //       ],
  //     },
  //     {
  //       title: 'pe-user-profile',
  //       link: '#',
  //       lang: 'pe-user-profile',
  //       children: [
  //         {
  //           title: 'pe-overview',
  //           lang: 'pe-overview',
  //           link: '/dashboard/pages-user',
  //           children: [],
  //         },
  //         {
  //           title: 'pe-activity',
  //           lang: 'pe-activity',
  //           link: '/dashboard/pages-user-activity',
  //           children: [],
  //         },
  //         {
  //           title: 'pe-followers',
  //           lang: 'pe-followers',
  //           link: '/dashboard/pages-user-followers',
  //           children: [],
  //         },
  //         {
  //           title: 'pe-documents',
  //           lang: 'pe-documents',
  //           link: '/dashboard/pages-user-documents',
  //           children: [],
  //         },
  //         {
  //           title: 'pe-notes',
  //           lang: 'pe-notes',
  //           link: '/dashboard/pages-user-notes',
  //           children: [],
  //         },
  //         {
  //           title: 'pe-projects',
  //           lang: 'pe-projects',
  //           link: '/dashboard/pages-user-projects',
  //           children: [],
  //         },
  //       ],
  //     },
  //     {
  //       title: 'pe-contact-us',
  //       lang: 'pe-contact-us',
  //       link: '/dashboard/pages-contact-us',
  //       children: [],
  //     },
  //     {
  //       title: "pe-faqs",
  //       lang: 'pe-faqs',
  //       link: '/pages-faq',
  //       children: [],
  //     },
  //     {
  //       title: 'pe-licenses',
  //       lang: 'pe-licenses',
  //       link: '/pages-licenses',
  //       children: [],
  //     },
  //     {
  //       title: 'pe-coming-soon',
  //       lang: 'pe-coming-soon',
  //       link: '/pages-coming-soon',
  //       children: [],
  //     },
  //     {
  //       title: 'pe-maintenance',
  //       lang: 'pe-maintenance',
  //       link: '/pages-maintenance',
  //       children: [],
  //     },
  //     {
  //       title: 'pe-privacy-policy',
  //       lang: 'pe-privacy-policy',
  //       link: '/pages-privacy-policy',
  //       children: [],
  //     },
  //     {
  //       title: 'pe-help-center',
  //       lang: 'pe-help-center',
  //       link: '/pages-help-center',
  //       children: [],
  //     }
  //   ],
  // },
  // {
  //   title: 'pe-support',
  //   lang: 'pe-support',
  //   icon: 'life-buoy',
  //   link: 'https://1.envato.market/domiex-admin-dashboard-support',
  //   children: [],
  // },
  // {
  //   title: 'pe-documentation',
  //   lang: 'pe-documentation',
  //   icon: 'file-text',
  //   link: 'https://srbthemes.kcubeinfotech.com/domiex/docs/angular/index.html',
  //   children: [],
  // },
  // {
  //   title: 'pe-changelog',
  //   lang: 'pe-changelog',
  //   icon: 'feather',
  //   link: 'https://srbthemes.kcubeinfotech.com/domiex/live/changelog.html',
  //   children: [],
  // },
  // {
  //   title: 'pe-changelog',
  //   lang: 'pe-changelog',
  //   icon: 'feather',
  //   link: 'https://srbthemes.kcubeinfotech.com/domiex/live/changelog.html',
  //   children: [],
  // },
];
