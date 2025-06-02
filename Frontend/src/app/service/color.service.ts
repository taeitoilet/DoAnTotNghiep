import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ColorService {

  constructor() { }

  rgbToHex(rgb: string): string {
    const rgbArray = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
    if (!rgbArray) {
      return rgb;
    }

    const hex = (x: string) => {
      return ("0" + parseInt(x).toString(16)).slice(-2);
    };

    return "#" + hex(rgbArray[1]) + hex(rgbArray[2]) + hex(rgbArray[3]);
  }

  getColorCodes(dataset:string[] ): string[] {
    const hashColorCodes: string[] = [];

    dataset.forEach((className: string) => {
      const element = document.querySelector(`.${className}`);
      let backgroundColor;

      if (element) {
        backgroundColor = window.getComputedStyle(element).backgroundColor;
      } else {
        const divElement = document.createElement('div');
        divElement.className = className;
        divElement.style.visibility = 'hidden';
        document.body.appendChild(divElement);

        const styles = window.getComputedStyle(divElement);
        backgroundColor = styles.backgroundColor;

        document.body.removeChild(divElement);
      }

      const hexColor = backgroundColor.includes('rgb') ? this.rgbToHex(backgroundColor) : backgroundColor;
      hashColorCodes.push(hexColor);
    });

    return hashColorCodes;
  }
}
