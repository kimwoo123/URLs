<template>
  <main>
  <q-card flat bordered class="my-card bg-grey-1" v-if="!isUpdating">
    <q-card-section>
      <div class="row items-center no-wrap justify-between">

        <div class="row">
          <q-avatar size="32px">
            <img :src="avatarUrl">
          </q-avatar>
          <div class="text-subtitle2">{{ memoItem.user.nickname}}</div>
        </div>

        <div class="col-auto" v-if="isAuthor">
          <q-btn color="grey-7" round flat icon="more_vert">
            <q-menu cover auto-close>
              <q-list>
                <q-item clickable @click="deleteMemo"> 
                  <q-item-section>삭제</q-item-section>
                </q-item>
                <q-item clickable @click="toggleUpdating">
                  <q-item-section>수정</q-item-section>
                </q-item>
              </q-list>
            </q-menu>
          </q-btn>
        </div>

      </div>
    </q-card-section>

    <q-separator/>

    <q-card-section v-if="!isUpdating">
      <div>{{ memoItem.content }}</div>
    </q-card-section>
    
  </q-card>

  <q-card flat bordered v-if="isUpdating">
    <q-card-section>
      <div class="row items-center no-wrap justify-between">
        <div class="row">
          <q-avatar size="32px">
            <img :src="avatarUrl">
          </q-avatar>
          <div class="text-subtitle2">{{ memoItem.user.nickname}}</div>
        </div>
      </div>
    </q-card-section>

    <q-card-section>
      <q-input borderless v-model="content" autofocus/>
    </q-card-section>

    <q-card-section>
      <div class="row q-gutter-xs justify-end">
        <q-btn 
          no-caps flat
          color="grey"
          label="취소" 
          class="col-4"
          @click="cancleUpdating"/>

        <q-btn 
          no-caps unelevated
          color="primary"
          label="등록" 
          class="col-4"
          @click="updateMemo"/>
      </div>
    </q-card-section>  
  </q-card>

  </main>
</template>

<script>
import { useStore } from 'vuex'
import { computed, ref } from 'vue'

export default {
  props: ['memoItem'],
  setup(props) {
    const $store = useStore()

    const avatarUrl = $store.state.user.avatar
    let content = ref(props.memoItem.content)
    const isUpdating = ref(false)

    const deleteMemo = () => {
      const memoData = {
        memos_id : $store.state.urls.selectedMemoId,
        memo_id : props.memoItem._id,
      }
      $store.dispatch('urls/DELETE_URL_MEMO', memoData)
    }

    const toggleUpdating = () => {
      isUpdating.value = !isUpdating.value
    }

    const cancleUpdating = () => {
      isUpdating.value = !isUpdating.value
      content.value = ref(props.memoItem.content)     
    }

    const updateMemo = () => {
      const memoData = {
        memos_id : $store.state.urls.selectedMemoId,
        memo_id : props.memoItem._id,
        content: content.value
      }
      console.log(memoData)
      $store.dispatch('urls/PUT_URL_MEMO', memoData)
      toggleUpdating()
    }

    const isAuthor = computed({
      get: () => {
        if ($store.state.user.useremail === props.memoItem.user.email) {
          return true
        } else {
          return false
        }
      }
    })
    return {
      deleteMemo,
      isAuthor,
      avatarUrl,
      content,
      updateMemo,
      isUpdating,
      toggleUpdating,
      cancleUpdating
    }
  }
}
</script>

<style>

</style>