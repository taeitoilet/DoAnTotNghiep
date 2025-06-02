import { Injectable } from '@angular/core';

declare var google: any;

@Injectable({
  providedIn: 'root'
})
export class GoogleMapsService {

  constructor() { }

  getDistance(currentLocation: {lat: number, lng: number}, targetLocation: {lat: number, lng: number}) {
    const origin = new google.maps.LatLng(currentLocation.lat, currentLocation.lng);
    const destination = new google.maps.LatLng(targetLocation.lat, targetLocation.lng);

    const service = new google.maps.DistanceMatrixService();

    return new Promise((resolve, reject) => {
      service.getDistanceMatrix(
        {
          origins: [origin],
          destinations: [destination],
          travelMode: google.maps.TravelMode.DRIVING,  // Bạn có thể thay đổi phương thức di chuyển (DRIVING, WALKING, BICYCLING, TRANSIT)
        },
        (response: google.maps.DistanceMatrixResponse, status: google.maps.DistanceMatrixStatus) => {
          if (status === 'OK') {
            const distance = response.rows[0].elements[0].distance.text;
            const duration = response.rows[0].elements[0].duration.text;
            resolve({ distance, duration });
          } else {
            reject('Unable to get distance');
          }
        }
      );
    });
  }

  calculateDistance(lat1: number, lon1: number, lat2: number, lon2: number): string {
    const R = 6371; // Bán kính trái đất (km)
    const dLat = this.degreesToRadians(lat2 - lat1);
    const dLon = this.degreesToRadians(lon2 - lon1);

    const a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(this.degreesToRadians(lat1)) * Math.cos(this.degreesToRadians(lat2)) *
      Math.sin(dLon / 2) * Math.sin(dLon / 2);

    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    const distance = R * c;
    return distance.toFixed(2);
  }

// Hàm chuyển độ sang radian
  degreesToRadians(degrees: number): number {
    return degrees * Math.PI / 180;
  }

}
