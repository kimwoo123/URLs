<template>
  <main>
    <!-- <auto-complete></auto-complete> -->
    <q-item>
      <q-item-section avatar>
        <q-icon name="recommend" size="40px" class="folder-icon" />
      </q-item-section>
      <q-item-section class="text-h6">
        추천 페이지
      </q-item-section>
    </q-item>

    <div>
      {{recommendUrls}}
    </div>

    <!-- <template
      v-for="urlItem in recommendUrls"
      :key="urlItem"
      class="col-xs-12 col-sm-6 col-md-4"
    >
      <folder-url-card :urlItem="urlItem" />
    </template> -->

  </main>
</template>

<script>
// import AutoComplete from 'src/components/AutoComplete.vue'
import { useStore } from 'vuex'
import { computed } from "vue";

export default {
  // components: { AutoComplete }
  setup() {
    const $store = useStore()
    $store.dispatch('recommend/GET_RECOMMEND_URL', 10)
    
    const recommendUrls = computed({
      get: () => $store.getters['recommend/recommendUrls']
    })

    return {
      recommendUrls
    }
  }
}
</script>