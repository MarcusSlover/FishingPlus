package me.marcusslover.fishingplus.exception

/**
 * Thrown when there's something wrong during the phase
 * of initializing a storage.
 */
class StorageException(val string: String) : Exception(string) {
}