<template>
  <main>
    <h1>url 다보여주겟따</h1>
  </main>
</template>

<script>
import { useStore } from 'vuex'

export default {
  setup() {
    const $store = useStore()
    $store.dispatch('urls/GET_ALL_URL')
  }
}
</script>

<style>

</style>