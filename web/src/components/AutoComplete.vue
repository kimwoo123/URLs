<template>
  <div class="q-pa-md">
    <div class="q-gutter-md">
      <q-select
        filled
        v-model="model"
        use-input
        use-chips
        input-debounce="0"
        label="Tag 검색"
        :options="options"
        @filter="filterFn"
        style="width: 250px"
      >
        <template v-slot:no-option>
          <q-item>
            <q-item-section class="text-grey">
              검색 결과가 없습니다.
            </q-item-section>
          </q-item>
        </template>
      </q-select>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import axios from 'axios'

const stringOptions = ['검색어를 입력해주세요.']

export default {
  setup () {
    const options = ref(stringOptions)
    const result = ref([])

    return {
      model: ref(null),
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
                axios.get(`http://localhost:8000/search?searchText=${val}`)
                .then((res) => {
                  result.value = []
                  res.data.hits.hits.map((hits) => {
                    result.value.push(hits._source['email'])
                  })
                  resolve(options.value = result.value)
                })
              })
            }
          })
        }, 1500)
      },
    }
  }
}
</script>
