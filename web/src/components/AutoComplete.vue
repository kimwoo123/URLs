<template>
  <div class="q-pa-md">
    <p>태그</p>
    <div class="q-gutter-md">
      <q-input v-model="selectTag" label="Tag 검색"/>
      <div>{{ selectTag }}</div>
      <div v-for="(urlItem, index) in searchPage" :key="index">
        <div v-if="urlItem._source">
          <folder-url-card :urlItem="urlItem._source"></folder-url-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useRoute } from "vue-router";
import { ref, watch, computed } from "vue";
import FolderUrlCard from "src/components/cards/FolderUrlCard.vue";
import { useStore } from "vuex";

export default {
  components: { FolderUrlCard },
  setup() {
    const $store = useStore();
    const $route = useRoute();
    const selectTag = ref("");
    const searchPage = computed({
      get: () => $store.getters["recommend/searchResult"]
    }) 

    watch(selectTag, val => {
      if (val !== "") {
        console.log(val)
        let queryData = { searchText: val, folderId: $route.params.folder_id };
        $store.dispatch("recommend/SEARCH_TAG", queryData);
        
      }
    });
    return {
      searchPage,
      selectTag,
    };
  }
};
</script>
