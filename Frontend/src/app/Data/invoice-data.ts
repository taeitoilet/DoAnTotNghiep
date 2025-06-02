import { Invoice } from '../Data/models';
export const invoiceData: Invoice[] = [
  {
    invoiceID: 'PE-10015',
    client: 'Joyce Perry',
    dateDue: '03 May, 2024',
    totalAmount: '$15,236',
    userStatus: 'Pending',
  },
  {
    invoiceID: 'PE-10016',
    client: 'Marilyn Ecklund',
    dateDue: '04 May, 2024',
    totalAmount: '$8,563',
    userStatus: 'New',
  },
  {
    invoiceID: 'PE-10017',
    client: 'Todd Day',
    dateDue: '08 May, 2024',
    totalAmount: '$7,986',
    userStatus: 'Successful',
  },
  {
    invoiceID: 'PE-10018',
    client: 'Mary Diaz',
    dateDue: '11 May, 2024',
    totalAmount: '$36,322',
    userStatus: 'Successful',
  },
  {
    invoiceID: 'PE-10019',
    client: 'Betty Gilliam',
    dateDue: '27 May, 2024',
    totalAmount: '$11,741',
    userStatus: 'Pending',
  },
  {
    invoiceID: 'PE-10020',
    client: 'Robert Olsen',
    dateDue: '05 June, 2024',
    totalAmount: '$13,465',
    userStatus: 'Successful',
  },
];
