global.browser = require('webextension-polyfill');

// storage 변경 시 데이터 갱신
chrome.storage.onChanged.addListener(() => {
  // refreshBadgeText();
});

console.log(process.env.NODE_ENV);
// 백그라운드에서 갱신 주기
const timer = null;
if (process.env.NODE_ENV === 'production') {
  // timer = setInterval(refreshBadgeText, 60 * 60 * 1000);
} else {
  // timer = setInterval(refreshBadgeText, 5 * 1000);
}

// 초기 시작을 위한 1회 실행
// refreshBadgeText();
