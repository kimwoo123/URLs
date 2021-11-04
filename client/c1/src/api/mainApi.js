import axios from 'axios';

axios.defaults.baseURL = 'http://k5b201.p.ssafy.io:4000';

// axios.interceptors.request.use(config => {
//   const accessToken = localStorage.getItem('access_token');
//   config.headers.common.Authorization = accessToken
//     ? `Bearer ${accessToken}`
//     : '';
//   return config;
// });

export default {
  getServerStatus() {
    let value = '';
    axios
      .get('/api')
      .then(res => {
        console.log(res);
        value = res;
      })
      .catch(error => {
        value = error;
      });
    return value;
  },
};
