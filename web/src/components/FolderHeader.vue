<template>
  <div class="row folder-header-container justify-between text-h6">
    <q-item>
      <q-icon name="folder" size="40px" class="folder-icon" />
      <span v-if="!isUpdatingFolderName">{{ folderName.value }}</span>

      <q-item-section v-if="isUpdatingFolderName">
        <form
          @submit.stop.prevent="updateFolderName"
          @reset.stop.prevent="toggleUpdating"
          class="row"
        >
          <q-input
            dense
            v-model="folderName.value"
            autofocus
            :rules="[ruleSameName, ruleMinWords, ruleMaxWords]"
            @keyup.enter="updateFolderName"
          />
          <q-btn
            flat
            round
            size="12px"
            icon="clear"
            color="negative"
            type="reset"
          />
          <q-btn
            flat
            round
            size="12px"
            icon="done"
            color="positive"
            type="submit"
          />
        </form>
      </q-item-section>
    </q-item>

    <q-item>
      <q-input
        filled
        dense
        bottom-slots
        v-model="searchText"
        label="folder 내 url 검색"
        maxlength="15"
      >
        <template v-slot:append>
          <q-icon
            v-if="searchText !== ''"
            name="close"
            @click="searchText = ''"
            class="cursor-pointer"
          />
          <q-icon name="search" @click="search" @keyup.enter="search" />
        </template>
      </q-input>

      <q-item-section>
        <q-btn flat round>
          <q-icon name="more_vert" />
          <q-menu cover auto-close self="bottom right">
            <q-list>
              <q-item clickable @click="alert = true">
                <q-item-section>폴더 삭제</q-item-section>
              </q-item>
              <q-item clickable @click="toggleUpdating">
                <q-item-section>폴더명 변경</q-item-section>
              </q-item>
            </q-list>
          </q-menu>
        </q-btn>
      </q-item-section>

      <folder-user-button :folderData="folderData" round />
      <create-url-button />
    </q-item>
  </div>

  <q-dialog v-model="alert">
    <q-card>
      <q-card-section class="row items-center">
        <q-avatar
          icon="delete"
          color="negative"
          text-color="white"
          size="24px"
        />
        <span class="q-ml-sm">폴더를 삭제하면 되돌릴 수 없습니다.</span>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="취소" color="primary" v-close-popup />
        <q-btn
          flat
          label="삭제하기"
          color="primary"
          v-close-popup
          @click="deleteFolder"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import { ref, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { useQuasar } from "quasar";
import FolderUserButton from "./buttons/folderUserButton.vue";
import CreateUrlButton from "./buttons/CreateUrlButton.vue";
import { store } from "quasar/wrappers";

export default {
  components: { FolderUserButton, CreateUrlButton },
  props: ["folderData"],
  setup(props) {
    const $store = useStore();
    const $router = useRouter();
    const $q = useQuasar();

    const originalFolderName = computed({
      get: () => $store.getters["urls/folderNow"].folder_name
    });

    const folderName = computed({
      get: () => {
        return ref($store.getters["urls/folderNow"].folder_name);
      },
      set: () => {
        console.log(folderName);
      }
    });

    const folderNameList = computed({
      get: () => {
        const arr = $store.state.urls.folders.map(x => x.folder_name);
        if (arr.includes(originalFolderName.value)) {
          const idx = arr.indexOf(originalFolderName.value);
          arr.splice(idx, 1);
          return arr;
        } else {
          return arr;
        }
      }
    });

    const isUpdatingFolderName = ref(false);

    const toggleUpdating = () => {
      folderName.value.value = $store.getters["urls/folderNow"].folder_name;
      isUpdatingFolderName.value = !isUpdatingFolderName.value;
    };

    const alert = ref(false);

    const deleteFolder = () => {
      $router.push({
        name: "AllUrls",
        params: { id: $store.state.user.userid }
      });
      $store.dispatch("urls/DELETE_FOLDER", props.folderData._id);
    };

    const updateFolderName = async () => {
      const folder_name = folderName.value.value;
      if (folderNameList.value.includes(folder_name)) {
        $q.notify({
          type: "negative",
          message: "같은 폴더 이름을 사용할 수 없어요!"
        });
      } else if (folder_name.length < 1 || !folder_name) {
        $q.notify({
          type: "negative",
          message: "폴더 이름을 한 글자 이상 입력해주세요."
        });
      } else if (folder_name.length > 10) {
        $q.notify({
          type: "negative",
          message: "폴더 이름을 10자 이하로 입력해주세요."
        });
      } else {
        if (folder_name !== originalFolderName.value) {
          const updatedFolderData = {
            id: props.folderData._id,
            folder_name: folder_name
          };
          $store.dispatch("urls/PUT_FOLDER_NAME", updatedFolderData);
        }
        toggleUpdating();
      }
    };

    const ruleSameName = val => {
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve(
            !folderNameList.value.includes(val) ||
              "같은 폴더 이름을 사용할 수 없어요."
          );
        }, 100);
      });
    };

    const ruleMinWords = val => {
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve(
            (val && val.length > 0) || "폴더 이름을 한 글자 이상 입력해주세요."
          );
        }, 100);
      });
    };

    const ruleMaxWords = val => {
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve(val.length < 11 || "폴더 이름을 10자 이하로 입력해주세요.");
        }, 100);
      });
    };

    const searchText = ref("");

    const search = () => {
      const urlData = {
        folder_id: $store.state.urls.folderNow._id,
        pattern: searchText.value
      };
      $store.dispatch("urls/GET_FOLDER_URL_SEARCH", urlData);
    };

    return {
      isUpdatingFolderName,
      toggleUpdating,
      folderName,
      updateFolderName,
      alert,
      deleteFolder,
      ruleMinWords,
      ruleSameName,
      ruleMaxWords,
      searchText,
      search
    };
  }
};
</script>

<style scoped lang="scss">
.folder-header-container {
  display: flex;
  align-items: center;
  padding: 15px 50px 0px 50px;
}
.folder-icon {
  margin-right: 10px;
}
</style>
