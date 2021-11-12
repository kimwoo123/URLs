<template>
  <div class="q-pa-md">
    <div class="q-gutter-md">
      <q-input 
      v-model="selectTag"
      label="Tag 검색"
      :options="options"
       />
      <div v-for="(tag, index) in searchResult" :key="index">
        <div @Click="tagUrl(tag)">
          {{ tag }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useRoute } from 'vue-router'
import { ref, watch } from 'vue'
import axios from 'axios'

const stringOptions = ['검색어를 입력해주세요.']

export default {
  setup () {
    const route = useRoute()
    const options = ref(stringOptions)
    const searchResult = ref([])
    const selectTag = ref('')

    const tagUrl = (tag) => {
      console.log(tag)
    }

    watch(selectTag, (val) => {
      setTimeout(() => {
          if (val === '') {
            options.value = stringOptions
          }
          else {
              return new Promise((resolve) => {
                searchResult.value = []
                // axios.get(`http://localhost:8000/search?searchText=${val}&folder=${route.params.id}`)
                axios.get(`http://localhost:8000/search?searchText=${val}&folder=6189d1da95bd3eee5ab6aa5b`)
                .then((res) => {
                  console.log(res.data[0]._source.urls)
                  res.data[0]._source.urls.map((url) => {
                    url.tags.map((tag) => {
                      searchResult.value.push(tag)
                    })
                  })
                })
              })
            }
      }, 700)
    })
    return {
      selectTag,
      searchResult,
      options,
      tagUrl,
    }
  },
}
</script>
