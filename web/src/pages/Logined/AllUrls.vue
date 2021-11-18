<template>
  <main>
    <folder-header :folderData="folderData" />

    <div
      class="row q-pa-md items-start q-gutter-lg justify-center"
      v-if="searchData.length === 0"
    >
      <template
        v-for="urlItem in folderData.urls"
        :key="urlItem.memos_id"
        class="col-xs-12 col-sm-6 col-md-4"
      >
        <folder-url-card :urlItem="urlItem" />
      </template>
    </div>

    <div
      class="row q-pa-md items-start q-gutter-lg justify-center"
      v-else
    >
      <template
        v-for="urlItem in searchData[0].urls"
        :key="urlItem.memos_id"
        class="col-xs-12 col-sm-6 col-md-4"
      >
        <folder-url-card :urlItem="urlItem" />
      </template>
    </div>

  </main>
</template>

<script>
import { computed, watch, onUnmounted } from "vue";
import { useStore } from 'vuex'
import { useRoute } from "vue-router";
import FolderUrlCard from "src/components/cards/FolderUrlCard.vue";
import FolderHeader from "src/components/FolderHeader.vue";


export default {
  components: { FolderUrlCard, FolderHeader },

  setup() {
    const $route = useRoute();
    const $store = useStore()
    $store.dispatch('urls/GET_ALL_URL')

    const searchData = computed({
      get: () => $store.state.urls.searchData
    });

    const folderData = computed({
      get: () => $store.getters["urls/folderNow"]
    });

    return {
      folderData,
      searchData
    };
  }
}
</script>

<style>

</style>