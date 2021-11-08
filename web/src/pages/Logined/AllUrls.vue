<template>
  <main>
    <h1>url 다보여주겟따</h1>
    <button @click="tokenme">토큰미</button>
  </main>
</template>

<script>
import { useStore } from 'vuex'
// import { urls } from 'src/api/index'
// import axios from 'axios'

export default {
  setup() {
    const $store = useStore()
    // const accessToken = $store.state.user.token

    // const tokenme = () => {
    //   console.log(accessToken)
    //   axios.get('http://k5b201.p.ssafy.io:4000/token/me',
    //     { headers: { Authorization: `Bearer ${accessToken}`} }
    //   )
    //   // urls.folderUrlMe()
    //   //   .then(async(result) => {
    //   //     console.log('실행이 안되니')
    //   //     console.log(result)
    //   //   })

    // }
    
    
    return {
      tokenme
    }
  }
}
</script>

<style>

</style>