export const listUrl = {
  baeURL: 'http://k5b201.p.ssafy.io:4000',
  release_url: 'http://k5b201.p.ssafy.io:4000/other',
};

export const CATEGORIES = [
  {type: 'CS', length: 1, value: 'Network', label: '네트워크'},
  {type: 'CS', length: 1, value: 'Database', label: '데이터베이스'},
  {type: 'CS', length: 1, value: 'Operation System', label: '운영체제'},
  {type: 'CS', length: 1, value: 'Data Structure', label: '자료구조'},
  {type: 'CS', length: 1, value: 'Algorithms', label: '알고리즘'},
  {type: 'CS', length: 1, value: 'AI/Big Data', label: '인공지능/빅데이터'},
  {type: 'CS', length: 1, value: 'Block chain', label: '블록체인'},
  {type: 'CS', length: 1, value: 'Mobile', label: '안드로이드/iOS'},
];
export const CATEGORY = [
  {
    label: 'Computer Science',
    options: CATEGORIES.filter(category => category.type === 'CS'),
  },
];
