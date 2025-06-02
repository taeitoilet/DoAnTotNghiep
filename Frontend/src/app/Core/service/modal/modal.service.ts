import {
  Injectable,
  ComponentFactoryResolver,
  ApplicationRef,
  Injector,
  EmbeddedViewRef,
  Type,
  ComponentRef,
} from '@angular/core';
import { AnimationBuilder, AnimationPlayer, style, animate, keyframes } from '@angular/animations';

@Injectable({
  providedIn: 'root',
})
export class ModalService {
  private modalRef: ComponentRef<any> | null = null;
  private closeCallback?: (result: any) => void;
  private backdropPlayer: AnimationPlayer | null = null;

  constructor(
    private cfr: ComponentFactoryResolver,
    private appRef: ApplicationRef,
    private injector: Injector,
    private animationBuilder: AnimationBuilder
  ) { }

  open(component: Type<any>, data: any = null, closeCallback?: (result: any) => void) {
    if (this.modalRef) {
      this.close();
    }

    this.closeCallback = closeCallback;

    // Create the modal component dynamically
    const factory = this.cfr.resolveComponentFactory(component);
    this.modalRef = factory.create(this.injector);
    this.modalRef.instance.config = data;

    this.appRef.attachView(this.modalRef.hostView);
    const modalElem = (this.modalRef.hostView as EmbeddedViewRef<any>).rootNodes[0] as HTMLElement;

    // Create backdrop
    const backdrop = document.createElement('div');
    backdrop.id = 'modalBackdropDiv';
    backdrop.className = 'backdrop-overlay';
    backdrop.style.position = 'fixed';
    backdrop.style.top = '0';
    backdrop.style.left = '0';
    backdrop.style.width = '100%';
    backdrop.style.height = '100%';
    backdrop.style.background = 'rgba(0, 0, 0, 0.5)';
    backdrop.style.zIndex = '5';

    backdrop.addEventListener('click', () => this.close());

    // Append backdrop to the modal container
    modalElem.querySelector('.modal')?.appendChild(backdrop);
    document.body.appendChild(modalElem);

    const fadeInBackdropAnimation = this.animationBuilder.build([
      style({ opacity: 0 }),
      animate('300ms ease-in', style({ opacity: 1 }))
    ]);
    this.backdropPlayer = fadeInBackdropAnimation.create(backdrop);
    this.backdropPlayer.play();
  }

  close(result?: any) {
    if (this.modalRef) {
      console.log('Closing modal with result:', result);
      // Create the fade-out animation for the modal and backdrop
      const fadeOutAnimation = this.animationBuilder.build([
        style({ opacity: 1 }),
        animate('300ms ease-out', keyframes([
          style({ opacity: 1, offset: 0 }),
          style({ opacity: 0, offset: 1 })
        ]))
      ]);

      // Get modal element and backdrop
      const modalElem = (this.modalRef.hostView as EmbeddedViewRef<any>).rootNodes[0] as HTMLElement;
      const backdrop = document.querySelector('#modalBackdropDiv') as HTMLElement;

      // Play the animation
      const modalPlayer = fadeOutAnimation.create(modalElem);
      const backdropPlayer = fadeOutAnimation.create(backdrop);

      // Play both animations
      modalPlayer.play();
      backdropPlayer.play();

      // Wait for the animation to complete before removing elements
      modalPlayer.onDone(() => {
        if (this.modalRef) { // Check again before accessing properties
          this.appRef.detachView(this.modalRef.hostView);
          this.modalRef.destroy();
          this.modalRef = null;

          // Remove the backdrop from the modal container
          if (backdrop) {
            backdrop.remove();
          }

          // Call the close callback if it exists
          console.log('Close call back:', this.closeCallback);
          if (this.closeCallback) {
            this.closeCallback(result); // Pass the result if needed
          }

          this.closeCallback = undefined; // Reset the callback
        }
      });
    }
  }


  // close(result: any = null) {

  //   if (this.modalRef) {
  //     const fadeOutAnimation = this.animationBuilder.build([
  //       style({ opacity: 1 }),
  //       animate('300ms ease-out', keyframes([
  //         style({ opacity: 1, offset: 0 }),
  //         style({ opacity: 0, offset: 1 })
  //       ]))
  //     ]);

  //     if (this.modalRef?.location?.nativeElement) {
  //       const modalPlayer = fadeOutAnimation.create(this.modalRef.location.nativeElement);
  //       modalPlayer.onDone(() => {
  //         this.appRef.detachView(this.modalRef!.hostView);
  //         this.modalRef!.destroy();
  //         this.modalRef = null;
  //         document.body.removeChild(document.getElementById('backDropDiv')!);
  //         if (this.closeCallback) {
  //           this.closeCallback(result);
  //           this.closeCallback = undefined;
  //         }
  //       });
  //       modalPlayer.play();
  //     } else {
  //       this.appRef.detachView(this.modalRef.hostView);
  //       this.modalRef.destroy();
  //       this.modalRef = null;
  //       document.body.removeChild(document.getElementById('backDropDiv')!);
  //       if (this.closeCallback) {
  //         this.closeCallback(result);
  //         this.closeCallback = undefined;
  //       }
  //     }
  //   }
  // }
}
