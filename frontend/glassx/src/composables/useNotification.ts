import type { Notification } from '@/components/NotificationSystem.vue'

interface NotificationSystem {
  addNotification: (notification: Omit<Notification, 'id'>) => void
}

let notificationSystemInstance: NotificationSystem | null = null

export const useNotification = () => {
  const setNotificationSystem = (system: NotificationSystem | null) => {
    notificationSystemInstance = system
  }

  const showNotification = (notification: Omit<Notification, 'id'>) => {
    if (notificationSystemInstance) {
      notificationSystemInstance.addNotification(notification)
    } else {
      console.warn('[useNotification] Notification system not initialized. Call setNotificationSystem first.')
    }
  }

  const clearNotificationSystem = () => {
    notificationSystemInstance = null
  }

  const success = (title: string, message?: string, duration?: number) => {
    showNotification({ type: 'success', title, message, duration })
  }

  const error = (title: string, message?: string, duration?: number) => {
    showNotification({ type: 'error', title, message, duration })
  }

  const warning = (title: string, message?: string, duration?: number) => {
    showNotification({ type: 'warning', title, message, duration })
  }

  const info = (title: string, message?: string, duration?: number) => {
    showNotification({ type: 'info', title, message, duration })
  }

  return {
    setNotificationSystem,
    showNotification,
    success,
    error,
    warning,
    info,
    clearNotificationSystem
  }
}