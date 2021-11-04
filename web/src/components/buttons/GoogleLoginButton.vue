<template>
  <q-btn 
    outline 
    rounded 
    color="primary" 
    icon="fab fa-google" 
    label="3초만에 시작하기"
    id="firebaseui-auth-container"
    @click="googleLogin"
  />
</template>

<script>
import { GoogleAuthProvider, getAuth, signInWithPopup } from 'firebase/auth';
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const $store = useStore()
    const $router = useRouter()

    const googleLogin = async() => {
      const provider = new GoogleAuthProvider()
      const auth = getAuth()

      signInWithPopup(auth, provider)
        .then(async(result) => {
          const credential = GoogleAuthProvider.credentialFromResult(result);
          const token = credential.accessToken;
          const useremail = result.user.email;
          const uid = result.user.uid
          console.log('credential', credential)
          console.log(token)
          console.log('result',result)
          // $store.commit("user/setUsername", useremail)
          const userData = {
            'token' : token,
            'useremail': useremail,
            'uid': uid
          }
          await $store.dispatch('user/LOGIN', userData);
          $router.push('/user')
        }).catch((error) => {
          console.log('팝업로그인실패', error)
          const errorCode = error.code;
          const errorMessage = error.errorMessage;
          const email = error.email;
          const credential = GoogleAuthProvider.credentialFromError(error);
        })
    }

    return {
      googleLogin
    }
  }
}

</script>

<style>

</style>