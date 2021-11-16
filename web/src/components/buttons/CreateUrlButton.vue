<template>
  <main>
    <q-item clickable v-ripple @click="openDialog">
      <q-item-section avatar>
        <q-icon name="create" />
      </q-item-section>
      <q-item-section>
        url 추가하기
      </q-item-section>
    </q-item>

    <q-dialog v-model="isOpen">
        <q-card style="min-width: 350px">
          <form>

          <q-card-section>
            <div class="text-h6">추가할 URL을 입력해주세요.</div>
          </q-card-section>
          <q-card-section class="q-pt-none">
            <q-input 
              v-model="urlName"
              dense 
              autofocus 
            />
            <q-input v-if="false"></q-input>
          </q-card-section>
          <q-btn flat label="태그 추천:" @click="recommendTag"/>
          <span v-for="(tag, index) in recommendResult" :key=index>
            <span>#{{ tag }}&nbsp;&nbsp;
            </span>
          </span>
          <q-card-section>
            <div class="text-h6">함께 저장할 태그를 입력해주세요.</div>
          </q-card-section>
          <q-card-section class="q-pt-none">
            <q-chips-input 
            v-model="model" 
            float-label="Floating Label" 
            />
            <q-input v-if="false"></q-input>
          </q-card-section>

          <q-card-actions align="right" class="text-primary">
            <q-btn flat label="취소" v-close-popup type="reset"/>
            <q-btn flat label="만들기" type="submit"/>
          </q-card-actions>

          </form>
        </q-card>
    </q-dialog>
  </main>
  
</template>

<script>
import { ref, computed } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'

export default {
  setup() {
    const $store = useStore()
    const $route = useRoute()
    const isOpen = ref(false)
    const urlName = ref ('')
    const recommendResult = computed({
      get: () => {
        return $store.state.recommend.recommendTag
      }
    }) 

    const recommendTag = () => {
      let recommendData = { url: urlName.value, count: 5, folderId: $route.params.folder_id }
      $store.dispatch('recommend/RECOMMEND_TAG', recommendData)
    }

    const openDialog = () => {
      isOpen.value = true
    }
    return {
      recommendTag,
      openDialog,
      recommendResult,
      isOpen,
      urlName,
    }
  }

}
</script>

<style>

</style>