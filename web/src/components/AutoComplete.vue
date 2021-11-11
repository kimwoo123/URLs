<template>
  <div class="q-pa-md">
    <div class="q-gutter-md">
      <q-input 
      v-model="selectTag"
      label="Tag 검색"
      :options="options"
       />
      <div v-for="(tag, index) in result" :key="index">
        {{ tag }}
      </div>
    </div>
  </div>
</template>

<script>
import { ref, watch } from 'vue'
import axios from 'axios'

const stringOptions = ['검색어를 입력해주세요.']

export default {
  setup () {
    const options = ref(stringOptions)
    const result = ref([])
    const selectTag = ref('')

    watch(selectTag, (val) => {
      setTimeout(() => {
          if (val === '') {
            options.value = stringOptions
          }
          else {
              return new Promise((resolve) => {
                result.value = []
                axios.get(`http://localhost:8000/search?searchText=${val}`)
                .then((res) => {
                  res.data.map((hits) => {
                    hits._source['urls'].map((url) => {
                      result.value.push(url.tags)
                    })
                  })
                })
              })
            }
      }, 700)
    })

    return {
      selectTag,
      result,
      options,
      filterFn (val, update) {
        setTimeout(() => {
          update(() => {
            if (val === '') {
              options.value = stringOptions
            }
            else {
              return new Promise((resolve) => {
                console.log(val)
                axios.get(`http://localhost:8000/search?searchText=${val}`)
                .then((res) => {
                  console.log('here')
                  console.log(res.data, 'here')
                  // result.value = []
                  // res.data.hits.hits.map((hits) => {
                  //   result.value.push(hits._source['email'])
                  // })
                  // resolve(options.value = result.value)
                })
                .catch((e) => {
                  conosle.log(e, 'here')
                })
              })
            }
          })
        }, 1500)
      },
    }
  },
}
</script>
