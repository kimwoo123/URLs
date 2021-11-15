
<template>
  <div class="q-pa-md text-h6">
    <q-item clickable >
      <q-item-section avatar>
        <q-icon name="folder" />
      </q-item-section>

      <q-item-section v-if="!isUpdatingFolderName">
        <div>
        {{ folderName.value }}
        <q-icon name="edit" />
        </div>
      </q-item-section>

      <q-popup-edit v-model="folderName.value" :validate="val => val.length > 1 && val.length" anchor="top start">
        <template v-slot="scope">
          <q-input
            autofocus
            dense
            v-model="scope.value"
            :model-value="scope.value"
            hint="폴더 이름을 입력해주세요."
            :rules="[
              val => scope.validate(scope.value) || '2글자 이상 입력해주세요.'
            ]"
            @keyup.enter="[updateFolderName(scope), scope.set]"
          >
            <template v-slot:after>
              <q-btn
                flat dense color="negative" icon="cancel"
                @click.stop="scope.cancel"
              />

              <q-btn
                flat dense color="positive" icon="check_circle"
                @click.stop="[updateFolderName(scope)]"
                :disable="scope.validate(scope.value) === false"
              />
            </template>
          </q-input>
        </template>
      </q-popup-edit>

      <q-item-section v-if="isUpdatingFolderName">
        <div>수정해라</div>
      </q-item-section>


    </q-item>

  </div>
</template>


<script>
import { ref, computed } from 'vue'
import { useStore } from 'vuex'

export default {
  props: ['folderData'],

  setup(props) {
    const $store = useStore()
    const folderName = computed({
      get: () => {
        return ref($store.getters['urls/folderNow'].folder_name)
        },
    })

    const refFolderName = ref(folderName.value)

    const updateFolderName = (result) => {
      const updatedFolderData = {
        id : props.folderData._id,
        folder_name: result.value
      }
      folderName.value.value = result.values
      result.set()
      $store.dispatch('urls/PUT_FOLDER_NAME', updatedFolderData)
    }

    const toggleUpdating = () => {

    }

    return {
      isUpdatingFolderName: ref(false),
      folderName,
      refFolderName,
      updateFolderName
    }
  }

}
</script>

<style>

</style>