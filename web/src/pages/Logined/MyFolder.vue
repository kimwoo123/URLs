<template>
  <main>
    <div>마이폴더2</div>
    <div>{{ folderId }}</div>
    <div>{{ folderUrls }}</div>

    <div class="row q-pa-md items-start q-gutter-md">
      <template 
        v-for="urlItem in folderUrls" 
        :key="urlItem.memos_id"
        class="col-xs-12 col-sm-6 col-md-4"
      >
        <folder-url-card :urlItem="urlItem"/>
      </template>
    </div>

  </main>
</template>

<script>
import { computed, watch } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import FolderUrlCard from 'src/components/cards/FolderUrlCard.vue'

export default {
  components: { FolderUrlCard },
  setup() {
    const $route = useRoute()
    const $store = useStore()

    const folderId = computed({
      get: () => {
        $store.dispatch('urls/GET_FOLDER_ULR', $route.params.folder_id)
        $store.dispatch('urls/CLOSE_MEMO')
        return $route.params.folder_id
        }
    })

    const folderUrls = computed({
      get: () => $store.state.urls.urls
    })


    return {
      folderId,
      folderUrls
    }
  }
}
</script>

<style>

</style>