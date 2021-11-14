<template>
  <div class="q-pa-md">
    <p>태그</p>
    <div class="q-gutter-md">
      <q-input 
      v-model="selectTag"
      label="Tag 검색"
      :options="options"
       />
      <span v-for="(tag, index) in searchResult" :key="index">
        <span @Click="tagUrl(tag)">
          {{ searchResult }}
        </span>
      </span>
      <p>My URLS</p>
      <div v-for="(urlItem, index) in searchPage" :key="index">
        <div>
          <folder-url-card :urlItem="urlItem"></folder-url-card>
          </div>
      </div>
      <q-input v-model="selectUrl" />
      <button @Click="createUrl">실험</button>
    </div>
  </div>
</template>

<script>
import { useRoute } from 'vue-router'
import { ref, watch } from 'vue'
import axios from 'axios'
import FolderUrlCard from 'src/components/cards/FolderUrlCard.vue'

const stringOptions = ['검색어를 입력해주세요.']

export default {
  components: { FolderUrlCard },
  setup () {
    const route = useRoute()
    const options = ref(stringOptions)
    const searchResult = ref([])
    const searchPage = ref([])
    const selectTag = ref('')
    const selectUrl = ref('')

    const createUrl = () => {
      axios.post(`http://localhost:8000/folder/${route.params.id}/url`, { url: selectUrl, thumbnail: "https://via.placeholder.com/200.jpg"})
      .then((res) => {
        console.log(res)
      })
      .catch((err) => {
        console.log(err)
      })
    }

    const tagUrl = (tag) => {
      console.log(tag)
    }

    watch(selectTag, (val) => {
      setTimeout(() => {
          if (val === '') {
            searchResult.value = stringOptions
          }
          else {
              return new Promise((resolve) => {
                searchResult.value = []
                searchPage.value = []
                // axios.get(`http://localhost:8000/search?searchText=${val}&folder=${route.params.id}`)
                axios.get(`http://localhost:8000/search?searchText=${val}&folder=6189d1da95bd3eee5ab6aa5b`)
                .then((res) => {
                  res.data[0]._source.urls.map((pageInfo) => {
                    searchPage.value.push(pageInfo)
                    pageInfo.tags.map((tag) => {
                      searchResult.value.push(tag)
                    })
                  })
                })
              })
            }
      }, 700)
    })
    return {
      searchResult,
      selectUrl,
      createUrl,
      selectTag,
      searchPage,
      options,
      tagUrl,
    }
  },
}
</script>
