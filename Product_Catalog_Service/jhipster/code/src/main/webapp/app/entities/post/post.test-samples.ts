import dayjs from 'dayjs/esm';

import { IPost, NewPost } from './post.model';

export const sampleWithRequiredData: IPost = {
  id: 8730,
  title: 'lawful above unless',
  content: '../fake-data/blob/hipster.txt',
  date: dayjs('2025-02-20T00:06'),
};

export const sampleWithPartialData: IPost = {
  id: 28272,
  title: 'unless or supportive',
  content: '../fake-data/blob/hipster.txt',
  date: dayjs('2025-02-20T14:04'),
};

export const sampleWithFullData: IPost = {
  id: 9917,
  title: 'clearly darn icy',
  content: '../fake-data/blob/hipster.txt',
  date: dayjs('2025-02-20T06:04'),
};

export const sampleWithNewData: NewPost = {
  title: 'willfully settler',
  content: '../fake-data/blob/hipster.txt',
  date: dayjs('2025-02-19T23:48'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
