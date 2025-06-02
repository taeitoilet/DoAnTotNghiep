import { Component, ElementRef, HostListener, ViewChild } from '@angular/core';
import { PageTitleComponent } from '../../../layouts/page-title/page-title.component';
import { CountUpModule } from 'ngx-countup';

import { LucideAngularModule } from 'lucide-angular';
import { ChartInfo } from '../../../componate/domix-charts/Model/Display-Chart.model';
import { DomixChartsComponent } from '../../../componate/domix-charts/domix-charts.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PRODUCTS } from '../../../Data/product-stock-list';
import { SimplebarAngularModule } from 'simplebar-angular';
import { RouterLink } from '@angular/router';
import 'jsvectormap';
import 'jsvectormap/dist/maps/world.js';
import jsVectorMap from 'jsvectormap';
import { DomixDropdownModule } from '../../../module/domix dropdown/domix-dropdown.module';
import { DomixTooltipModule } from '../../../module/domix tooltip/domix-tooltip.module';
import { LayoutSettingService } from '../../../layouts/layout-setting.service';
import {ReportApiServiceService} from "../../../Core/service/report-api-service/report-api-service.service";
import {DailyReport, DishSale, MonthlyReport, WeeklyReport} from "../../../Core/service/model/report";

interface Marker {
  name: string;
  coords: [number, number];
}

interface Product {
  productsID: string; // Ensure this property is included
  productName: string;
  stock: number;
  price: string;
  status: string;
}

@Component({
    selector: 'app-index',
    imports: [
        PageTitleComponent,
        CountUpModule,
        LucideAngularModule,
        DomixChartsComponent,
        FormsModule,
        CommonModule,
        DomixTooltipModule,
        SimplebarAngularModule,
        RouterLink,
        DomixDropdownModule,
    ],
    templateUrl: './index.component.html',
    styleUrl: './index.component.scss'
})
export class IndexComponent {
  options = { autoHide: true };
  products: Product[] = [];
  displayedProducts: DishSale[] = [];
  sortBy: keyof DishSale = 'totalQuantity';
  sortDirection: 'desc' | 'asc' = 'desc';
  currentPage: number = 1;
  itemsPerPage: number = 8;
  openTab = 1;
  donutChart!: ChartInfo;
  basicChart!: ChartInfo
  netProfitChart!: ChartInfo
  trafficChart!: ChartInfo
  currentMonth : number = 0
  previousRevenue : MonthlyReport = new MonthlyReport()
  currentRevenue : MonthlyReport = new MonthlyReport()
  percent : number = 0
  dailyReport : DailyReport[] = [];
  weeklyReport: WeeklyReport[] = [];
  monthlyReport: MonthlyReport[] = [];
  activeReportType : string = 'dailyReport'
  dishSaleType : string = 'Hôm nay'
  orderSaleType : string = 'Hôm nay'
  dishSale : DishSale[] = [];
  orders : number = 0
  constructor(private settingService: LayoutSettingService,public reportService : ReportApiServiceService) {
  }

  ngAfterViewInit() {
    this.initMap();
  }

  initMap() {
    const mapsConfig = [
      {
        selector: '#dashboardMap',
        map: 'world',
        markersSelectable: true,
        markers: [
          {
            coords: [-14.235, -51.9253],
          },
          {
            coords: [35.8617, 104.1954],
          },
          {
            coords: [61, 105],
          },
        ],
        markerStyle: {
          initial: {
            fill: '#3f4d67',
          },
          hover: {
            fill: '#04a9f5',
          },
        },
        markerLabelStyle: {
          initial: {
            fontFamily: "'Inter', sans-serif",
            fontSize: 13,
            fontWeight: 500,
            fill: '#3f4d67',
          },
        },
      },
    ];
    // Initialize each map based on its configuration
    mapsConfig.forEach((config) => {
      new jsVectorMap({
        selector: config.selector,
        map: config.map,
        markers: config.markers || [],
        markerStyle: config.markerStyle || {},
        markerLabelStyle: config.markerLabelStyle || {},
      });
    });
  }
  ngOnInit(): void {
    // this.loadProducts();
    // this.updateDisplayedProducts();
    this.getMonthlyReport();
    this.getMonthlyDishSale()
    this.getMonthlyOrder()
  }

  renderChart(): void {
    const revenueData = this.dailyReport.map(r => r.totalRevenue);
    const netProfitData = this.dailyReport.map(r => r.netRevenue);
    const categories = this.dailyReport.map(r => r.timeRange);

    this.donutChart = {
      series: [44, 55, 41, 17, 15],
      chartOptions: {
        chart: {
          height: 90,
          type: 'donut',
        },
        dataLabels: {
          enabled: false,
        },
        colors: [],
        dataSet: [
          'bg-primary-500',
          'bg-green-500',
          'bg-yellow-500',
          'bg-red-500',
          'bg-purple-500'
        ],
        legend: {
          show: false,
          position: 'bottom',
        },
        grid: {
          padding: {
            top: -6,
            right: 0,
            bottom: -10,
            left: 0,
          },
        },
      },
    };
    this.basicChart = {
      series: [
        {
          name: 'Lợi nhuận',
          data: netProfitData,
        },
        {
          name: 'Doanh thu',
          data: revenueData,
        },
      ],
      chartOptions: {
        chart: {
          height: 280,
          type: 'bar',
          toolbar: {
            show: false,
          },
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '55%',
            borderRadius: 10,
          },
        },
        states: {
          hover: {
            filter: {
              type: 'none',
            },
          },
        },
        dataLabels: {
          enabled: false,
        },
        stroke: {
          show: true,
          width: 2,
          lineCap: 'round',
          colors: ['transparent'],
        },
        colors: [],
        dataSet: ['bg-red-500', 'bg-primary-500'],
        xaxis: {
          categories: categories,
        },
        yaxis: {
          title: {
            text: 'Việt Nam Đồng',
          },
        },
        tooltip: {
          y: {
            formatter: (val: number) => `${val.toLocaleString('en-US')} VNĐ`,
          },
        },
      },
    };
    this.netProfitChart = {
      series: [
        {
          name: 'Profit',
          data: [5, 4, 7, 2, 8, 6, 3],
        },
      ],
      chartOptions: {
        chart: {
          height: 143,
          type: 'bar',
          toolbar: {
            show: false,
          },
          sparkline: { enabled: true },
        },
        plotOptions: {
          bar: {
            horizontal: false,
          },
        },
        grid: {
          padding: {
            top: 0,
            right: -10,
            bottom: 0,
            left: -10,
          },
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent'],
        },
        dataSet: ['bg-primary-500', 'bg-green-500'],
        colors: [],
        tooltip: {
          y: {
            formatter: (val: number) => `${val}`,
          },
        },
      },
    };
    this.trafficChart = {
      series: [
        {
          name: 'Sales',
          data: [
            0.4, 0.65, 0.76, 0.88, 1.5, 2.1, 2.9, 3.8, 3.9, 4.2, 4, 4.3, 4.1, 4.2,
            4.5, 3.9, 3.5,
          ],
        },
        {
          name: 'Visit',
          data: [
            -0.8, -1.05, -1.06, -1.18, -1.4, -2.2, -2.85, -3.7, -3.96, -4.22,
            -4.3, -4.4, -4.1, -4, -4.1, -3.4, -3.1,
          ],
        },
      ],
      chartOptions: {
        chart: {
          height: 320,
          type: 'bar',
          stacked: true,
          toolbar: {
            show: false,
          },
        },
        colors: [],
        dataSet: ['bg-sky-500', 'bg-indigo-500'],
        plotOptions: {
          bar: {
            horizontal: true,
            barHeight: '80%',
          },
        },
        dataLabels: {
          enabled: false,
        },
        grid: {
          strokeDashArray: 2,
          xaxis: {
            lines: {
              show: false,
            },
          },
          yaxis: {
            lines: {
              show: true,
            },
          },
          padding: {
            top: -20,
            bottom: 0,
          },
          row: {
            opacity: 0,
          },
        },
        yaxis: {
          min: -5,
          max: 5,
        },
        states: {
          hover: {
            filter: {
              type: 'none',
            },
          },
        },
        tooltip: {
          shared: false,
          x: {
            formatter: (val: any) => val,
          },
          y: {
            formatter: (val: any) => Math.abs(val) + '%',
          },
        },
        xaxis: {
          categories: [
            'Jan',
            'Feb',
            'Mar',
            'Apr',
            'May',
            'Jun',
            'Jul',
            'Aug',
            'Sep',
            'Oct',
            'Nov',
            'Dec',
          ],
          labels: {
            formatter: (val: any) => Math.abs(Math.round(val)) + '%',
          },
        },
      },
    };
  }

  renderWeeklyChart(): void {
    const revenueData = this.weeklyReport.map(r => r.totalRevenue);
    const netProfitData = this.weeklyReport.map(r => r.netRevenue);
    const categories = this.weeklyReport.map(r => r.date);

    this.donutChart = {
      series: [44, 55, 41, 17, 15],
      chartOptions: {
        chart: {
          height: 90,
          type: 'donut',
        },
        dataLabels: {
          enabled: false,
        },
        colors: [],
        dataSet: [
          'bg-primary-500',
          'bg-green-500',
          'bg-yellow-500',
          'bg-red-500',
          'bg-purple-500'
        ],
        legend: {
          show: false,
          position: 'bottom',
        },
        grid: {
          padding: {
            top: -6,
            right: 0,
            bottom: -10,
            left: 0,
          },
        },
      },
    };
    this.basicChart = {
      series: [
        {
          name: 'Lợi nhuận',
          data: netProfitData,
        },
        {
          name: 'Doanh thu',
          data: revenueData,
        },
      ],
      chartOptions: {
        chart: {
          height: 280,
          type: 'bar',
          toolbar: {
            show: false,
          },
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '55%',
            borderRadius: 10,
          },
        },
        states: {
          hover: {
            filter: {
              type: 'none',
            },
          },
        },
        dataLabels: {
          enabled: false,
        },
        stroke: {
          show: true,
          width: 2,
          lineCap: 'round',
          colors: ['transparent'],
        },
        colors: [],
        dataSet: ['bg-red-500', 'bg-primary-500'],
        xaxis: {
          categories: categories,
        },
        yaxis: {
          title: {
            text: 'Việt Nam Đồng',
          },
        },
        tooltip: {
          y: {
            formatter: (val: number) => val.toLocaleString('vi-VN') + ' VNĐ',
          },
        },
      },
    };
    this.netProfitChart = {
      series: [
        {
          name: 'Profit',
          data: [5, 4, 7, 2, 8, 6, 3],
        },
      ],
      chartOptions: {
        chart: {
          height: 143,
          type: 'bar',
          toolbar: {
            show: false,
          },
          sparkline: { enabled: true },
        },
        plotOptions: {
          bar: {
            horizontal: false,
          },
        },
        grid: {
          padding: {
            top: 0,
            right: -10,
            bottom: 0,
            left: -10,
          },
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent'],
        },
        dataSet: ['bg-primary-500', 'bg-green-500'],
        colors: [],
        tooltip: {
          y: {
            formatter: (val: number) => `${val}`,
          },
        },
      },
    };
    this.trafficChart = {
      series: [
        {
          name: 'Sales',
          data: [
            0.4, 0.65, 0.76, 0.88, 1.5, 2.1, 2.9, 3.8, 3.9, 4.2, 4, 4.3, 4.1, 4.2,
            4.5, 3.9, 3.5,
          ],
        },
        {
          name: 'Visit',
          data: [
            -0.8, -1.05, -1.06, -1.18, -1.4, -2.2, -2.85, -3.7, -3.96, -4.22,
            -4.3, -4.4, -4.1, -4, -4.1, -3.4, -3.1,
          ],
        },
      ],
      chartOptions: {
        chart: {
          height: 320,
          type: 'bar',
          stacked: true,
          toolbar: {
            show: false,
          },
        },
        colors: [],
        dataSet: ['bg-sky-500', 'bg-indigo-500'],
        plotOptions: {
          bar: {
            horizontal: true,
            barHeight: '80%',
          },
        },
        dataLabels: {
          enabled: false,
        },
        grid: {
          strokeDashArray: 2,
          xaxis: {
            lines: {
              show: false,
            },
          },
          yaxis: {
            lines: {
              show: true,
            },
          },
          padding: {
            top: -20,
            bottom: 0,
          },
          row: {
            opacity: 0,
          },
        },
        yaxis: {
          min: -5,
          max: 5,
        },
        states: {
          hover: {
            filter: {
              type: 'none',
            },
          },
        },
        tooltip: {
          shared: false,
          x: {
            formatter: (val: any) => val,
          },
          y: {
            formatter: (val: any) => Math.abs(val) + '%',
          },
        },
        xaxis: {
          categories: [
            'Jan',
            'Feb',
            'Mar',
            'Apr',
            'May',
            'Jun',
            'Jul',
            'Aug',
            'Sep',
            'Oct',
            'Nov',
            'Dec',
          ],
          labels: {
            formatter: (val: any) => Math.abs(Math.round(val)) + '%',
          },
        },
      },
    };
  }
  renderMonthlyChart(): void {
    const revenueData = this.monthlyReport.map(r => r.totalRevenue);
    const netProfitData = this.monthlyReport.map(r => r.netRevenue);
    const categories = this.monthlyReport.map(r => `Tháng ${r.monthNumber}`);

    this.donutChart = {
      series: [44, 55, 41, 17, 15],
      chartOptions: {
        chart: {
          height: 90,
          type: 'donut',
        },
        dataLabels: {
          enabled: false,
        },
        colors: [],
        dataSet: [
          'bg-primary-500',
          'bg-green-500',
          'bg-yellow-500',
          'bg-red-500',
          'bg-purple-500'
        ],
        legend: {
          show: false,
          position: 'bottom',
        },
        grid: {
          padding: {
            top: -6,
            right: 0,
            bottom: -10,
            left: 0,
          },
        },
      },
    };
    this.basicChart = {
      series: [
        {
          name: 'Lợi nhuận',
          data: netProfitData,
        },
        {
          name: 'Doanh thu',
          data: revenueData,
        },
      ],
      chartOptions: {
        chart: {
          height: 280,
          type: 'bar',
          toolbar: {
            show: false,
          },
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '55%',
            borderRadius: 10,
          },
        },
        states: {
          hover: {
            filter: {
              type: 'none',
            },
          },
        },
        dataLabels: {
          enabled: false,
        },
        stroke: {
          show: true,
          width: 2,
          lineCap: 'round',
          colors: ['transparent'],
        },
        colors: [],
        dataSet: ['bg-red-500', 'bg-primary-500'],
        xaxis: {
          categories: categories,
        },
        yaxis: {
          title: {
            text: 'Việt Nam Đồng',
          },
        },
        tooltip: {
          y: {
            formatter: (val: number) => `${val.toLocaleString('en-US')} VNĐ`,
          },
        },
      },
    };
    this.netProfitChart = {
      series: [
        {
          name: 'Profit',
          data: [5, 4, 7, 2, 8, 6, 3],
        },
      ],
      chartOptions: {
        chart: {
          height: 143,
          type: 'bar',
          toolbar: {
            show: false,
          },
          sparkline: { enabled: true },
        },
        plotOptions: {
          bar: {
            horizontal: false,
          },
        },
        grid: {
          padding: {
            top: 0,
            right: -10,
            bottom: 0,
            left: -10,
          },
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent'],
        },
        dataSet: ['bg-primary-500', 'bg-green-500'],
        colors: [],
        tooltip: {
          y: {
            formatter: (val: number) => `${val}`,
          },
        },
      },
    };
    this.trafficChart = {
      series: [
        {
          name: 'Sales',
          data: [
            0.4, 0.65, 0.76, 0.88, 1.5, 2.1, 2.9, 3.8, 3.9, 4.2, 4, 4.3, 4.1, 4.2,
            4.5, 3.9, 3.5,
          ],
        },
        {
          name: 'Visit',
          data: [
            -0.8, -1.05, -1.06, -1.18, -1.4, -2.2, -2.85, -3.7, -3.96, -4.22,
            -4.3, -4.4, -4.1, -4, -4.1, -3.4, -3.1,
          ],
        },
      ],
      chartOptions: {
        chart: {
          height: 320,
          type: 'bar',
          stacked: true,
          toolbar: {
            show: false,
          },
        },
        colors: [],
        dataSet: ['bg-sky-500', 'bg-indigo-500'],
        plotOptions: {
          bar: {
            horizontal: true,
            barHeight: '80%',
          },
        },
        dataLabels: {
          enabled: false,
        },
        grid: {
          strokeDashArray: 2,
          xaxis: {
            lines: {
              show: false,
            },
          },
          yaxis: {
            lines: {
              show: true,
            },
          },
          padding: {
            top: -20,
            bottom: 0,
          },
          row: {
            opacity: 0,
          },
        },
        yaxis: {
          min: -5,
          max: 5,
        },
        states: {
          hover: {
            filter: {
              type: 'none',
            },
          },
        },
        tooltip: {
          shared: false,
          x: {
            formatter: (val: any) => val,
          },
          y: {
            formatter: (val: any) => Math.abs(val) + '%',
          },
        },
        xaxis: {
          categories: [
            'Jan',
            'Feb',
            'Mar',
            'Apr',
            'May',
            'Jun',
            'Jul',
            'Aug',
            'Sep',
            'Oct',
            'Nov',
            'Dec',
          ],
          labels: {
            formatter: (val: any) => Math.abs(Math.round(val)) + '%',
          },
        },
      },
    };
  }

  getDailyOrder(){
    this.orderSaleType = 'Hôm nay'
    this.reportService.getDailyOrder().subscribe((data: any) => {
      this.orders = data.data;
    })
  }
  getWeeklyOrder(){
    this.orderSaleType = 'Tuần này'
    this.reportService.getWeeklyOrder().subscribe((data: any) => {
      this.orders = data.data;
    })
  }
  getMonthlyOrder(){
    this.orderSaleType = 'Tháng này'
    this.reportService.getMonthlyOrder().subscribe((data: any) => {
      this.orders = data.data;
    })
  }
  getYearlyOrder(){
    this.orderSaleType = 'Năm nay'
    this.reportService.getYearlyOrder().subscribe((data: any) => {
      this.orders = data.data;
    })
  }
  getDailyDishSale(){
    this.dishSaleType = 'Hôm nay'
    this.reportService.getDailySale().subscribe((data: any) => {
      this.dishSale = data.data.items;
      this.updateDisplayedProducts();
    })
  }
  getWeeklyDishSale(){
    this.dishSaleType = 'Tuần này'
    this.reportService.getWeeklySale().subscribe((data: any) => {
      this.dishSale = data.data.items;
      this.updateDisplayedProducts();
    })
  }
  getMonthlyDishSale(){
    this.dishSaleType = 'Tháng này'
    this.reportService.getMonthlySale().subscribe((data: any) => {
      this.dishSale = data.data.items;
      this.updateDisplayedProducts();
    })
  }
  getYearlyDishSale(){
    this.dishSaleType = 'Năm này'
    this.reportService.getYearlySale().subscribe((data: any) => {
      this.dishSale = data.data.items;
      this.updateDisplayedProducts();
    })
  }
  loadProducts(): void {
    // Use imported data
    this.products = PRODUCTS;
    this.updateDisplayedProducts();
  }

  sort(column: keyof DishSale): void {
    this.sortDirection =
      this.sortBy === column
        ? this.sortDirection === 'asc'
          ? 'desc'
          : 'asc'
        : 'asc';
    this.sortBy = column;
    this.updateDisplayedProducts();
  }

  updateDisplayedProducts(): void {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;

    this.displayedProducts = this.dishSale
      .sort((a, b) => {
        const key = this.sortBy as keyof DishSale; // Ensure TypeScript understands `this.sortBy` is a valid key
        const valueA = a[key];
        const valueB = b[key];

        // Handle comparison based on type
        if (typeof valueA === 'string' && typeof valueB === 'string') {
          return this.sortDirection === 'asc'
            ? valueA.localeCompare(valueB)
            : valueB.localeCompare(valueA);
        } else if (typeof valueA === 'number' && typeof valueB === 'number') {
          return this.sortDirection === 'asc'
            ? valueA - valueB
            : valueB - valueA;
        } else {
          return 0;
        }
      })
      .slice(startIndex, endIndex);
  }

  prevPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updateDisplayedProducts();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updateDisplayedProducts();
    }
  }

  gotoPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.updateDisplayedProducts();
    }
  }

  get totalPages(): number {
    return Math.ceil(this.dishSale.length / this.itemsPerPage);
  }

  get showingStart(): number {
    return (this.currentPage - 1) * this.itemsPerPage + 1;
  }

  get showingEnd(): number {
    return Math.min(this.currentPage * this.itemsPerPage, this.dishSale.length);
  }
  products2 = [
    {
      name: 'Sleeve Clothing Leggings',
      image: 'assets/images/products/img-05.png',
      link: 'apps-ecommerce-product-overview.html',
      rating: 4.5,
      price: '$22.00',
    },
    {
      name: 'Bra Lace Crop top',
      image: 'assets/images/products/img-06.png',
      link: 'apps-ecommerce-product-overview.html',
      rating: 5,
      price: '$29.99',
    },
    {
      name: 'Tote bag Leather Handbag Dolce',
      image: 'assets/images/products/img-08.png',
      link: 'apps-ecommerce-product-overview.html',
      rating: 5,
      price: '$79.99',
    },
    {
      name: 'Sneakers Shoe Nike Basketball',
      image: 'assets/images/products/img-11.png',
      link: 'apps-ecommerce-product-overview.html',
      rating: 4.5,
      price: '$32.00',
    },
  ];

  getStars(rating: number): number[] {
    return Array(Math.floor(rating)).fill(0);
  }

  newMessages = [
    {
      name: 'John Davis',
      text: 'Hello, How are you?',
      time: '04:25 PM',
      avatar: 'assets/images/avatar/user-11.png',
    },
    {
      name: 'Jordan Davis',
      text: 'Here are some of very cute illustration.',
      time: '11:20 AM',
      avatar: 'assets/images/avatar/user-3.png',
    },
    {
      name: 'Taylor Wilson',
      text: 'Use tools like Trello, Asana, or Jira for task management and progress tracking.',
      time: '10:49 AM',
      avatar: 'assets/images/avatar/user-4.png',
    },
    {
      name: 'Jane Brown',
      text: 'Regularly review and improve communication practices based on team feedback and project needs.',
      time: '03:32 AM',
      avatar: 'assets/images/avatar/user-5.png',
    },
    {
      name: 'Cameron Wilson',
      text: 'Schedule regular check-ins to address any roadblocks and keep everyone aligned.',
      time: '11:05 PM',
      avatar: 'assets/images/avatar/user-6.png',
    },
  ];

  allMessages = [
    {
      name: 'Sirkka Hakola',
      text: 'Hello, How are you?',
      time: '04:25 PM',
      avatar: 'assets/images/avatar/user-14.png',
    },
    {
      name: 'Jordan Davis',
      text: 'Here are some of very cute illustration.',
      time: '11:20 AM',
      avatar: 'assets/images/avatar/user-15.png',
    },
    {
      name: 'Nicholas Hope',
      text: 'Use tools like Trello, Asana, or Jira for task management and progress tracking.',
      time: '10:49 AM',
      avatar: 'assets/images/avatar/user-16.png',
    },
    {
      name: 'Susan Liles',
      text: 'Regularly review and improve communication practices based on team feedback and project needs.',
      time: '03:44 AM',
      avatar: 'assets/images/avatar/user-17.png',
    },
    {
      name: 'David Johnson',
      text: 'Schedule regular check-ins to address any roadblocks and keep everyone aligned.',
      time: '09:57 PM',
      avatar: 'assets/images/avatar/user-18.png',
    },
  ];

  getDailyReport(){
    this.activeReportType = 'dailyReport'
    this.reportService.getDailyReport().subscribe((data: any) => {
      this.dailyReport = data.data.items;
      this.renderChart()
    })
  }
  getWeeklyReport(){
    this.activeReportType = 'weeklyReport'
    this.reportService.getWeeklyReport().subscribe((data: any) => {
      this.weeklyReport = data.data.items;
      this.renderWeeklyChart()
    })
  }
  getMonthlyReport(){

    this.activeReportType = 'monthlyReport'

    this.reportService.getMonthlyReport().subscribe((data: any) => {
      this.monthlyReport = data.data.items;
      this.renderMonthlyChart()
      this.currentMonth = new Date().getMonth() + 1;
      this.previousRevenue = this.monthlyReport.find(r => r.monthNumber === this.currentMonth - 1)!
      this.currentRevenue = this.monthlyReport.find(r => r.monthNumber === this.currentMonth)!
      if(this.previousRevenue?.totalRevenue != 0){
        this.percent = ((this.currentRevenue?.totalRevenue - this.previousRevenue?.totalRevenue)/this.previousRevenue?.totalRevenue) * 100
      }else{
        this.percent = 100
      }
    })
  }

}
