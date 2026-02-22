<template>
  <!-- Fixed positioning modal overlay - ensures proper centering relative to viewport -->
  <div
    v-if="show"
    class="modal-overlay"
    role="dialog"
    aria-modal="true"
    :aria-labelledby="titleId"
    :aria-describedby="messageId"
  >
    <!-- Background mask -->
    <div class="modal-backdrop" @click="handleCancel"></div>
    
    <!-- Dialog container -->
    <div class="modal-dialog" ref="dialogRef">
      <!-- 标题 -->
      <div class="flex items-center justify-between mb-4">
        <h3 :id="titleId" class="text-lg font-semibold text-white">{{ title }}</h3>
        <button 
          @click="handleCancel"
          class="text-white/60 hover:text-white transition-colors"
          :aria-label="$t('common.close')"
        >
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
          </svg>
        </button>
      </div>
      
      <!-- 内容 -->
      <div class="mb-6">
        <p :id="messageId" class="text-white/80">{{ message }}</p>
      </div>
      
      <!-- 操作按钮 -->
      <div class="flex gap-3 justify-end">
        <button 
          ref="cancelBtnRef"
          @click="handleCancel"
          class="px-4 py-2 text-white/80 hover:text-white border border-white/20 hover:border-white/40 rounded-md transition-colors"
        >
          {{ cancelText || $t('common.cancel') }}
        </button>
        <button 
          ref="confirmBtnRef"
          @click="handleConfirm"
          :class="[
            'px-4 py-2 rounded-md transition-colors',
            type === 'danger' 
              ? 'bg-red-500 hover:bg-red-600 text-white' 
              : 'bg-blue-500 hover:bg-blue-600 text-white'
          ]"
        >
          {{ confirmText || $t('common.confirm') }}
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Modal overlay - ensures proper viewport-relative positioning */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 9999;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 1rem;
}

/* Background backdrop */
.modal-backdrop {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  backdrop-filter: blur(4px);
}

/* Dialog container */
.modal-dialog {
  position: relative;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 0.5rem;
  padding: 1.5rem;
  max-width: 28rem;
  width: 100%;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25);
  /* Ensure dialog stays centered */
  margin: auto;
}
</style>

<script setup lang="ts">
import { ref, watch, nextTick, onUnmounted, useId } from 'vue'

// Generate unique IDs for ARIA attributes
const titleId = useId()
const messageId = useId()

interface Props {
  show: boolean
  title: string
  message: string
  confirmText?: string
  cancelText?: string
  type?: 'danger' | 'warning' | 'info'
}

const props = withDefaults(defineProps<Props>(), {
  confirmText: '',
  cancelText: '',
  type: 'danger'
})

const emit = defineEmits<{
  confirm: []
  cancel: []
}>()

// Refs for focus management
const dialogRef = ref<HTMLElement | null>(null)
const cancelBtnRef = ref<HTMLButtonElement | null>(null)
const confirmBtnRef = ref<HTMLButtonElement | null>(null)

// Store the previously focused element
let previouslyFocusedElement: HTMLElement | null = null

const handleConfirm = () => {
  emit('confirm')
}

const handleCancel = () => {
  emit('cancel')
}

// Handle Escape key press
const handleKeyDown = (event: KeyboardEvent) => {
  if (event.key === 'Escape') {
    handleCancel()
  }
}

// Focus management
watch(() => props.show, async (newShow) => {
  if (newShow) {
    // Store the currently focused element
    previouslyFocusedElement = document.activeElement as HTMLElement
    
    // Add keyboard event listener
    document.addEventListener('keydown', handleKeyDown)
    
    // Focus the cancel button when dialog opens
    await nextTick()
    cancelBtnRef.value?.focus()
  } else {
    // Remove keyboard event listener
    document.removeEventListener('keydown', handleKeyDown)
    
    // Restore focus to the previously focused element
    if (previouslyFocusedElement) {
      previouslyFocusedElement.focus()
      previouslyFocusedElement = null
    }
  }
})

// Cleanup on unmount
onUnmounted(() => {
  document.removeEventListener('keydown', handleKeyDown)
})
</script> 
