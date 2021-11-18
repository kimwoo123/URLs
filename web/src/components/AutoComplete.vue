<template>
  <div class="q-pa-md">
    <p>태그</p>
    <div class="q-gutter-md">
      <q-input v-model="selectTag" label="Tag 검색" :options="options" />
      <span v-for="(tag, index) in searchResult" :key="index">
        <span @Click="tagUrl(tag)">
          {{ searchResult }}
        </span>
      </span>
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
import { ref, watch } from "vue";
import FolderUrlCard from "src/components/cards/FolderUrlCard.vue";
import { useStore } from "vuex";

const stringOptions = ["검색어를 입력해주세요."];

export default {
  components: { FolderUrlCard },
  setup() {
    const $store = useStore();
    const $route = useRoute();
    const options = ref(stringOptions);
    const searchResult = ref([]);
    const selectTag = ref("");
    const searchPage = computed({
      get: () => $store.getters["recommend/searchResult"]
    }) 


    watch(selectTag, val => {
      if (val !== "") {
        let queryData = { searchText: val, folderId: $route.params.folder_id };
        $store.dispatch("recommend/SEARCH_TAG", queryData);
        
      }
    });
    return {
      searchResult,
      searchPage,
      selectTag,
      options,
    };
  }
};
</script>
