import {Component, EventEmitter, Output} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {GoogleMapsModule, MapMarker} from "@angular/google-maps";

@Component({
  selector: 'app-map-modal',
  imports: [
    GoogleMapsModule,
    MapMarker
  ],
  templateUrl: './map-modal.component.html',
  styleUrl: './map-modal.component.scss'
})
export class MapModalComponent {
  zoom = 15;
  center: google.maps.LatLngLiteral = { lat: 21.0278, lng: 105.8342 }; // Mặc định Hà Nội
  markerPosition: google.maps.LatLngLiteral | null = null;


  constructor(private dialogRef: MatDialogRef<MapModalComponent>) {}

  mapClicked(event: google.maps.MapMouseEvent) {
    if (event.latLng) {
      this.markerPosition = {
        lat: event.latLng.lat(),
        lng: event.latLng.lng()
      };
    }
  }

  onConfirm() {
    if (this.markerPosition) {
      this.dialogRef.close(this.markerPosition); // Trả dữ liệu khi đóng modal
    } else {
      alert('Vui lòng chọn vị trí trên bản đồ trước khi xác nhận.');
    }
  }
}
