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
// import * as firebaseui from 'firebaseui'
// import { initializeApp } from 'firebase/app'
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
        .then((result) => {
          const credential = GoogleAuthProvider.credentialFromResult(result);
          const token = credential.accessToken;
          const user = result.user;
          const useremail = result.user.email;
          $store.commit("user/setUsername", useremail)
          $router.push('/user')
        }).catch((error) => {
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