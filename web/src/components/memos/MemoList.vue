<template>
  <div class="column q-pa-sm q-gutter-sm">
    <div class="text-h6">메모 / 타이틀</div>
    <div v-for="memoItem in memos" :key="memoItem._id">
      <url-memo :memoItem="memoItem"/>
    </div>
    <new-memo/>
  </div>
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import NewMemo from './NewMemo.vue'
import UrlMemo from './UrlMemo.vue'

export default {
  components: { NewMemo, UrlMemo},
  setup() {
    const $store = useStore()
    const $router = useRouter()

    const memos = computed({
      get: () => $store.state.urls.memos
    })
  
    return {
      memos,
    }
  }
}
</script>

<style>

</style>