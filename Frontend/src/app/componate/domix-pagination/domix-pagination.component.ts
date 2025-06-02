import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, SimpleChange, SimpleChanges } from '@angular/core';
import { LucideAngularModule } from 'lucide-angular';

@Component({
    selector: 'app-domix-pagination',
    imports: [CommonModule, LucideAngularModule],
    templateUrl: './domix-pagination.component.html',
    styleUrl: './domix-pagination.component.scss'
})
export class DomixPaginationComponent {
  @Input() currentPage: number = 1;
  @Input() totalPages: number = 1;
  @Input() showingStart: number|null = 1;
  @Input() showingEnd: number | null = 1;
  @Input() totalResults: number = 0;

  @Output() pageChanged: EventEmitter<number> = new EventEmitter<number>();

  ngOnChanges(changes: SimpleChanges) {
    if (changes['totalResults']) {
      const totalResultsChange = changes['totalResults'];

      if (totalResultsChange.currentValue !== totalResultsChange.previousValue) {
        setTimeout(() => {
          this.currentPage = 1;
          this.pageChanged.emit(this.currentPage);
        });
      }
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.pageChanged.emit(this.currentPage - 1);
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.pageChanged.emit(this.currentPage + 1);
    }
  }

  gotoPage(page: number) {
    this.pageChanged.emit(page);
  }

  get visiblePages(): number[] {
    const total = this.totalPages;
    const current = this.currentPage;
    const delta = 2; // số lượng trang hiển thị xung quanh trang hiện tại

    const pages: number[] = [];

    // Nếu tổng số trang nhỏ, hiển thị tất cả
    if (total <= 7) {
      for (let i = 1; i <= total; i++) {
        pages.push(i);
      }
      return pages;
    }

    // Luôn hiển thị trang đầu và cuối
    pages.push(1);

    // Thêm dấu ... nếu cần
    if (current - delta > 2) {
      pages.push(-1); // đại diện cho "..."
    }

    const start = Math.max(2, current - delta);
    const end = Math.min(total - 1, current + delta);

    for (let i = start; i <= end; i++) {
      pages.push(i);
    }

    if (current + delta < total - 1) {
      pages.push(-1); // đại diện cho "..."
    }

    pages.push(total);

    return pages;
  }

}
