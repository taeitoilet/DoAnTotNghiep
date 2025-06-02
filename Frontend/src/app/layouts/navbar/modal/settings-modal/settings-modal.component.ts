import { Component } from '@angular/core';
import { ModalService } from '../../../../Core/service/modal/modal.service';
import { LucideAngularModule } from 'lucide-angular';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LayoutSettingService } from '../../../layout-setting.service';

@Component({
    selector: 'app-settings-modal',
    imports: [LucideAngularModule, FormsModule, CommonModule],
    templateUrl: './settings-modal.component.html',
    styleUrls: ['./settings-modal.component.scss']
})
export class SettingsModalComponent {
  // layout: string = 'default';
  // navType: string = '';
  // sidebar: string = '';
  // sidebarColors: string = 'light';
  // contentWidth: string = 'default';
  // layoutMode: string = 'light';
  // layoutDir: string = 'ltr';
  // darkModeColors: string = '';
  // primaryColors: string = 'default';
  // darkModeColorsOld: string = '';
  // navTypeOld: string = 'default';
  // sidebarOld: string = 'default';
  // sidebarColorsOld: string = 'light';
  // scrollableDiv: HTMLElement | null = null; // Initialize this if you have a reference

  settings = {
    layout: 'default',
    contentWidth: 'default',
    sidebarSize: 'default',
    navigationType: 'default',
    direction: 'ltr',
    mode: 'light',
    sidervarAssetColors: 'light',
    primaryAssetColors: 'blue',
    darkModeColor: 'default',
  }

  constructor(private modalService: ModalService, private settingService: LayoutSettingService) {
    console.trace();
    this.settingService.settings$.subscribe((settings) => {
      this.settings = settings;
    });
  }

  closeSettingsModal() {
    this.modalService.close();
  }

  layouts = [
    { text: 'Default', value: 'default' },
    { text: 'Horizontal', value: 'horizontal' },
    { text: 'Modern', value: 'modern' },
    { text: 'Boxed', value: 'boxed' },
    { text: 'Semibox', value: 'semibox' },
  ]

  navigationType = [
    { text: 'Default', value: 'default' },
    { text: 'Floating', value: 'floating' },
    { text: 'Boxed', value: 'boxed' },
    { text: 'Pattern', value: 'pattern' },
  ]

  sidebarType = [
    { text: 'Default (Large)', value: 'default' },
    { text: 'Medium', value: 'medium' },
    {
      text: 'Small', value: 'small' },
  ]

  direction = [
    { text: 'LTR', value: 'ltr' },
    { text: 'RTL', value: 'rtl' },
  ]

  modes = [
    { text: 'Light', value: 'light', class:'block w-full mb-3 overflow-hidden cursor-pointer card h-28 peer-checked:border-primary-500' },
    { text: 'Dark', value: 'dark', class:'block w-full mb-3 overflow-hidden cursor-pointer border-dark-700 bg-dark-950 card h-28 peer-checked:border-primary-500' },
    { text: 'Default System', value: 'auto', class:'relative block w-full mb-3 overflow-hidden cursor-pointer card h-28 peer-checked:border-primary-500 before:absolute before:bg-gray-950 before:w-1/2 before:inset-y-0 before:right-0' },
    { text: 'Black & White', value: 'black-white', class:'block w-full mb-3 overflow-hidden cursor-pointer card h-28 peer-checked:border-primary-500 grayscale'},
  ]

  darkModeColor = [
    {
      text: 'default', value: 'default', class: 'flex items-center justify-center border border-gray-200 rounded-full dark:border-dark-800 input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400'
    },
    { text: 'zinc', value: 'zinc', class: 'rounded-full bg-zinc-950 input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
    { text: 'stone', value: 'stone', class: 'rounded-full bg-stone-950 input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
    { text: 'neutral', value: 'neutral', class: 'rounded-full bg-neutral-950 input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
    { text: 'gray', value: 'gray', class: 'rounded-full bg-gray-950 input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
  ]

  sideBarColor = [
    { text: 'light', value: 'light', class:'bg-gray-100 rounded-full input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400'},
    { text: 'dark', value: 'dark', class:'bg-gray-800 rounded-full input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
    { text: 'brand', value: 'brand', class:'rounded-full bg-primary-900 input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
    { text: 'purple', value: 'purple', class:'rounded-full bg-purple-950 input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
    { text: 'sky', value: 'sky', class: 'rounded-full bg-sky-950 input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
  ]

  primaryColor = [
    { text: 'default', value: 'default', class:'rounded-full bg-[#358ffc] input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
    { text: 'green', value: 'green', class: 'bg-[#1acd81] rounded-full input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
    { text: 'violet', value: 'violet', class: 'rounded-full bg-violet-500 input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
    { text: 'orange', value: 'orange', class: 'rounded-full bg-[#f04b1f] input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
    { text: 'teal', value: 'teal', class: 'bg-teal-500 rounded-full input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
    { text: 'fuchsia', value: 'fuchsia', class: 'rounded-full bg-fuchsia-500 input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
    { text: 'lime', value: 'lime', class: 'rounded-full bg-lime-500 input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
    { text: 'amber', value: 'amber', class: 'rounded-full bg-amber-500 input-radio-label size-10 peer-checked:ring-1 peer-checked:ring-offset-2 dark:peer-checked:ring-offset-dark-900 peer-checked:ring-primary-400' },
  ]

  containerWidth = [
    { text: 'Default', value: 'Default' },
    { text: 'Fluid', value: 'fluid' },
  ]

  setLayout(mode: string) {
    this.settingService.updateSettings({ layout: mode });
  }

  setNavigation(mode: string) {
    this.settingService.updateSettings({ navigationType: mode });
  }

  setContentWidth(mode: string) {
    this.settingService.updateSettings({ contentWidth: mode });
  }

  setSidebar(mode: string) {
    console.log(mode);
    this.settingService.updateSettings({ sidebarSize: mode });
  }

  setDir(mode: string) {
    this.settingService.updateSettings({ direction: mode });
  }

  setLayoutMode(mode: string) {
    this.settingService.updateSettings({ mode: mode });
  }

  setDarkModeColors(mode: string) {
    this.settingService.updateSettings({ darkModeColor: mode });
  }

  setSidebarColors(mode: string) {
    this.settingService.updateSettings({ sidervarAssetColors: mode });
  }

  setPrimaryColors(mode: string) {
    this.settingService.updateSettings({ primaryAssetColors: mode });
  }

  resetLayout(): void {
    this.settingService.resetSettings();
  }


  // setLayout(
  //   newLayout: 'horizontal' | 'default' | 'modern' | 'boxed' | 'semibox'
  // ) {
  //   this.layout = newLayout;

  //   const layouts = {
  //     horizontal: () => {
  //       this.storeOldValues();
  //       this.clearNavAndSidebar();
  //     },
  //     default: () => {
  //       this.restoreNavAndSidebar();
  //     },
  //     modern: () => {
  //       this.restoreNavAndSidebar();
  //     },
  //     boxed: () => {
  //       this.storeOldValues();
  //       this.restoreNavAndSidebar();
  //     },
  //     semibox: () => {
  //       this.storeOldValues();
  //       this.restoreNavAndSidebar();
  //     },
  //   };

  //   (layouts[newLayout] || this.restoreOldValues.bind(this))();
  //   this.updateAttributes();
  // }

  // setNavigation(newNavType: string) {
  //   this.navType = newNavType;
  //   this.updateAttributes();
  // }

  // setContentWidth(newContentWidth: string) {
  //   this.contentWidth = newContentWidth;
  //   this.updateAttributes();
  // }

  // setSidebar(newSidebar: string) {
  //   this.sidebar = newSidebar;
  //   this.updateAttributes();
  // }

  // setDirection(newLayoutDir: string) {
  //   this.layoutDir = newLayoutDir;
  //   this.updateAttributes();
  // }

  // setLayoutMode(newLayoutMode: string) {
  //   this.layoutMode = newLayoutMode;
  //   localStorage.setItem('curLayoutMode', newLayoutMode);

  //   if (newLayoutMode === 'auto') {
  //     const colorSchemeMediaQuery = window.matchMedia(
  //       '(prefers-color-scheme: dark)'
  //     );
  //     if (colorSchemeMediaQuery.matches) {
  //       this.layoutMode = 'dark';
  //     }
  //   }

  //   if (this.layoutMode !== 'dark') {
  //     this.sidebarColors = this.sidebarColorsOld;
  //     this.clearDarkModeColors();
  //   } else {
  //     this.sidebarColorsOld = this.sidebarColors;
  //     this.sidebarColors = 'light';
  //     this.applyDarkModeColors();
  //   }

  //   this.updateAttributes(true);
  // }

  // setDarkModeColors(newDarkModeColors: string) {
  //   this.clearDarkModeColors();
  //   this.darkModeColorsOld = newDarkModeColors;
  //   this.darkModeColors = newDarkModeColors;
  //   this.updateAttributes(true);
  // }

  // setSidebarColors(newSidebarColors: string) {
  //   this.sidebarColors = newSidebarColors;
  //   this.updateAttributes();
  // }

  // setPrimaryColors(newColors: string) {
  //   this.primaryColors = newColors;
  //   this.updateAttributes(true);
  // }

  // resetAttributes() {
  //   this.layout = 'default';
  //   this.navType = 'default';
  //   this.contentWidth = 'default';
  //   this.layoutMode = 'light';
  //   this.sidebar = 'default';
  //   this.sidebarColors = 'light';
  //   this.layoutDir = 'ltr';
  //   this.primaryColors = 'default';
  //   this.darkModeColors = '';
  //   this.updateAttributes();
  // }

  // updateAttributes(isResize: boolean = false) {
  //   const documentElement = document.documentElement;
  //   documentElement.setAttribute('data-layout', this.layout);
  //   documentElement.setAttribute('data-content-width', this.contentWidth);
  //   documentElement.setAttribute('data-mode', this.layoutMode);
  //   documentElement.setAttribute('data-sidebar-colors', this.sidebarColors);
  //   if (this.sidebar) {
  //     documentElement.setAttribute('data-sidebar', this.sidebar);
  //   } else {
  //     documentElement.removeAttribute('data-sidebar');
  //   }
  //   if (this.navType) {
  //     documentElement.setAttribute('data-nav-type', this.navType);
  //   } else {
  //     documentElement.removeAttribute('data-nav-type');
  //   }
  //   documentElement.setAttribute('dir', this.layoutDir);
  //   documentElement.setAttribute('data-colors', this.primaryColors);

  //   if (this.darkModeColors) {
  //     documentElement.classList.add(this.darkModeColors);
  //   }

  //   if (this.scrollableDiv) {
  //     this.scrollableDiv.addEventListener('wheel', (e) =>
  //       this.handleMenuScroll(e)
  //     );
  //   }

  //   // Save settings to localStorage
  //   localStorage.setItem('layout', this.layout);
  //   localStorage.setItem('navType', this.navType);
  //   localStorage.setItem('contentWidth', this.contentWidth);
  //   localStorage.setItem('layoutMode', this.layoutMode);
  //   localStorage.setItem('sidebar', this.sidebar);
  //   localStorage.setItem('layoutDir', this.layoutDir);
  //   localStorage.setItem('colors', this.primaryColors);

  //   if (isResize) {
  //     setTimeout(() => {
  //       window.dispatchEvent(new Event('resize'));
  //     }, 0);
  //   }
  // }

  // private storeOldValues() {
  //   this.navTypeOld = this.navType || 'default';
  //   this.sidebarOld = this.sidebar || 'default';
  //   this.sidebarColorsOld = this.sidebarColors || 'light';
  // }

  // private clearNavAndSidebar() {
  //   this.navType = '';
  //   this.sidebar = '';
  // }

  // private restoreNavAndSidebar() {
  //   if (this.navType !== '') {
  //     this.navTypeOld = this.navType;
  //   }
  //   this.navType = '';
  //   this.sidebar = this.sidebar || this.sidebarOld;
  //   this.sidebarColors = this.sidebarColorsOld || this.sidebarColors;
  // }

  // private restoreOldValues() {
  //   this.navType = this.navTypeOld;
  //   this.sidebar = this.sidebarOld;
  // }

  // private clearDarkModeColors() {
  //   if (this.darkModeColorsOld) {
  //     document.documentElement.classList.remove(this.darkModeColorsOld);
  //   }
  // }

  // private applyDarkModeColors() {
  //   if (this.darkModeColorsOld) {
  //     document.documentElement.classList.add(this.darkModeColorsOld);
  //   }
  // }

  // private handleMenuScroll(event: WheelEvent) {}
}
